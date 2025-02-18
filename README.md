# saml-poc
POC of a Spring Boot app supporting REST, protobuf, and SAML with Keycloak as IDP.

## Keycloak IdP Setup
Run Keycloak in Docker at port 8080 with login/password of `admin/admin`:
```shell
docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.0.8 start-dev
```
### Keycloak encryption setup
This command creates two files `local.key` and `local.crt`.  Move the two files to the `src/main/resources` folder.
```shell
openssl req -newkey rsa:2048 -nodes -keyout local.key -x509 -days 365 -out local.crt
```

### Keycloak SAML setup

1. Create a realm named `saml-poc`:
   1. Click on the drop-down in the upper left corner and select `Create realm`.
   2. Name it `saml-poc`.
2. Create a client named `saml-poc` of type SAML with the following settings:
    1. Client ID: `saml-poc`
    2. Name: Keeper SAML test client
    3. Root URL: http://localhost:9080
    4. Home URL: /
    5. Valid redirect URIs: /*
3. In the `Keys` tab, turn on "Client signature required".
4. In the `Keys` tab, click `Import key` and select the `local.crt` file.
5. In the `application.yaml` file: 
   - set the `private-key-location` property to `classpath:local.key`.
   - set the `public-key-location` property to `classpath:local.crt`.
6. Click on the `Users` left menu item.
7. Create a user:
   1. Click on `Add user`.
   2. Fill in the `Username` and `Email` fields at least.
   3. Click on the `Credentials` tab and fill in the `Password` and `Password confirmation` fields and turn "Temporary" off.

### Test user login
```shell
./gradlew bootRun
```

1. In a browser, go to http://localhost:9080
2. It should forward to the Keycloak `saml-poc` login page.
3. Sign in with the test user you created above.
4. You should see the message: `You are successfully logged in as <test-username>`.