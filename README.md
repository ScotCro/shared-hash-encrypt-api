# shared-hash-encrypt-api

## Overview
The shared-hash-encrypt-api project provides a RESTful service API with endpoints for hashing clear text, verifying hashes against their original clear text, and symmetric encrypt and decrypt capabilities all through a RESTful API.

## Github
Should you have not acquired this software through Github it is available there at https://github.com/ScotCro/shared-hash-encrypt-api.

## License
This software is provided by ScotCro LLC through the MIT license. The actual license file, `LICENSE.txt` can be found in the same folder as this file, `README.md`.

## Setup and Running on Google Cloud

### Prerequisites
The `shared-hash-encrypt-api` is a RESTful micro-services API that is built out of the box to run in Google Cloud on App Engine Standard. It makes use of a MySQL database instance as written and makes use of Google Cloud KMS for encryption/decryption key management. As such, you'll need to have some software installed locally and some things already set up in Google Cloud.

### Software Requirements
In order to build and deploy the shared-hash-encrypt-api to Google Cloud AppEngine you'll need to have the following

 * Java 11
 * Maven 3+
 * gcloud cli tools

 Describing how to install and configure these is beyond the scope of this document. 

### Access to a Google Cloud project
Since we'll be deploying this shared-hash-encrypt-api to Google Cloud App Engine Standard you need a project in GC to which you or someone you can get to help you has Editor access or appropriate admin access to
 
 * Google Cloud SQL MySQL
 * Google Cloud KMS
 * Google Cloud App Engine Standard
 
### Google Cloud SQL MySQL database
You'll need access to a Google Cloud SQL MySQL database instance upon which you can create a new schema. This database and the shared-hash-encrypt-api should be in the same project in GCP. Then you'll need to

 1. Create a new schema shared-encrypt-decrypt
 2. Create a new user shared-hash-encrypt-api and remember the password you gave it
 3. Grant access for this new user to the shared-encrypt-decrypt schema granting it CREATE TEMPORARY TABLES, DELETE, EXECUTE, INSERT, LOCK TABLES, SELECT and UPDATE
 4. Execute the DDL SQL file in the project at shared-hash-encrypt-api-serevices/src/support/db/schema/shared-hash-encrypt-api-mysql-ddl.sql when connected as the root user on the previously created schema.
 
### Client API keys
In order to make calls to the shared-hash-encrypt-api a client will need a client API key. Simply a special string when passed in the header named X-CLIENT-API-KEY grants access to the API. To create a new client API key all you need to do is insert a new row into the client\_api\_keys table created in the database schema above. The value of the api key can be any string. I generate a GUUID and then hash that value using the hash as the client\_api\_key. It is in your best interest to give each caller accessing the API a different client API key so you can tell the callers apart in case of abuse. **You will use this client API key to make calls to the API going forward, so don't misplace it or make it publicly available in any way.**

### Google Cloud KMS key
The application will need access to a Google Cloud KMS encryption/decryption key. You'll need to enable the Cryptographic Keys API in the GC console under Security > Cryptographic Keys. There you will need to create a key ring and key. Note that you'll want the location or region on the key ring to match the location or region in which your DB is running and your App Engine Application. When you create your key it should have settings for software protection level and a purpose of Symmetric Encryption/Decryption. Set up or don't setup a rollover schedule as you see fit.

### Service Account IAM Setup
In order to use the KMS key you created the App Engine Service account will need to be granted the role of Cloud KMS CryptoKey Encrypter/Decrypter. The service account will have an account name like `{project-id}@appspot.gserviceaccount.com`. 

### application.properties
Once you have all of the previous preparation done you can find the `application.properties` file in `src/main/resources`. It will be filled with placeholder values. You will need to replace the placeholder values with information gleened from the efforts previously described herein.

You'll notice one property in the properties file

```code
spring.datasource.password=ENC({encrypted-database-password})

```

has a value enclosed in `ENC()`. This is because this isn't the actual property value, but an encrypted value. It is in this case the encrypted password value of the MySQL user 'shared-hash-encrypt-api' which the application uses to connect to the DB. You'll need to encrypt or hash the value of the password you remembered when you created that MySQL account to do so you'll need another value.

An encryption key stored in the src/main/appengine/app.yaml file is used as the key for encrypting this password. The key of which is `env_variables.JASYPT_ENCRYPTOR_PASSWORD`. You should pick a value for this encryption key and place it in the app.yaml file.

Then you'll execute the following command substituting the db user's password and the encryption key into the command instead of the placeeholders. The command being

```bash
java -cp ~/.m2/repository/org/jasypt/jasypt/1.9.2/jasypt-1.9.2.jar \  org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
input='{password-to-encrypt}' password='{encryption-key}' algorithm=PBEWithMD5AndDES
```

### Building and deploying to GC App Engine Standard
Assuming you have Java 11, Maven and the gcloud command line tools setup AND you have your gcloud config set to point to your project you can build and deploy the API to GCP by doing

```bash
cd {project-folder}
mvn clean install
cd shared-hash-encrypt-api-services
APP_DEPLOY_VERSION=`date +%Y%m%d%H%M%S`; \
  mvn -Dapp.deploy.projectId={your-project-id} \
  -Dapp.deploy.version=$APP_DEPLOY_VERSION clean package appengine:deploy
```

Assuming all goes well you'll have a new app engine service running in your project named shared-hash-encrypt-api.
## API Reference

### Monitoring endpoints
*GET https://host[:port][/context]/alive*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |

#### Response
200 OK

```json
{
  "alive": true
}
```

Any other response status code is an error

*GET https://host[:port][/context]/ready*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |

#### Response
200 OK

```json
{
  "endpointsAliveMap": {},
  "databaseConnectionActive": true
}
```

Any other response status code is an error

---

### Hashing endpoint

*POST https://host[:port][/context]/hashes*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body
```json
{
  "clearText" : "{some-clear-text-to-be-hashed}"
}
```

#### Response
200 OK

```json
{
  "codes" : ["0"],
  "messages" : ["success"],
  "data" : {
    "hashedText" : "some-hash-rendered-from-the-clear-text-and-fixed-salt"
  }
}
```
Any other response status code is an error

---

### Clear text and hash validation

*POST https://host[:port][/context]/hash-cleartext-validations*

#### HEADERS
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body
```json
{
  "clearText" : "some clear text to be hashed"
}
```

#### Response
200 OK
##### Clear text with hash is **valid**

```json
{
    "codes": ["200"],
    "messages": ["VALID"],
    "successful": true
}
```

##### Clear text with hash is **invalid**
```json
{
    "codes": ["0"],
    "messages": ["INVALID"],
    "successful": false
}
```

Any other response status code is an error

---

### Encrypting a single string

*POST https://host[:port][/context]/encryptions*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body

```json
{
  "clearText" : "clear-text"
}
```

#### Response

200 OK

```json
{
    "codes": [
        "200"
    ],
    "messages": [
        "OK"
    ],
    "data": {
        "encryptedAndBase64EncodedText": "{encrypted-value-for-clear-text}"
    },
    "successful": true
}
```

Any other response status code is an error

---

### Decrypting a single string

*POST https://host[:port][/context]/decryptions*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body

```json
{
  "encryptedAndBase64EncodedText" : "{some-encrypted-string}"
}
```

#### Response

200 OK

```json
{
    "codes": [
        "200"
    ],
    "messages": [
        "OK"
    ],
    "data": {
        "clearText": "{clear-text-for-encrypted-string}"
    },
    "successful": true
}
```

Any other response status code is an error

---

### Encrypting a list of clear text strings

*POST https://host[:port][/context]/list-encryptions*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body

```json
{
  "clearTextValues" : ["clear-1", "clear-2", "clear-3"]
}
```

#### Response

200 OK

```json
{
    "codes": [
        "200"
    ],
    "messages": [
        "OK"
    ],
    "data": [
        "{encrypted-value-for-clear-1}",
        "{encrypted-value-for-clear-2}",
        "{encrypted-value-for-clear-3}"
    ],
    "successful": true
}
```

Any other response status code is an error

---

### Decrypting a list of encrypted strings

*POST https://host[:port][/context]/list-decryptions*

#### Headers
| Name | Value |
| ---- | ----- |
| Accept | application/json |
| Content-Type | application/json |
| X-CLIENT-API-KEY | {client-api-key-from-DB-client-api-keys-table} |

#### Request body

```json
{
  "encryptedAndBase64EncodedTextValues" : [
    "{some-encrypted-string-1}", 
    "{some-encrypted-string-2}", 
    "{some-encrypted-string-3}"
  ]
}
```

#### Response

200 OK

```json
{
    "codes": [
        "200"
    ],
    "messages": [
        "OK"
    ],
    "data": [
        "{clear-text-1}",
        "{clear-text-2}",
        "{clear-text-3}"
    ],
    "successful": true
}
```

Any other response status code is an error
