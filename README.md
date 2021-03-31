# Medical clinic authorization service

> Backend part of application simulating the work of an information system for document management of a medical clinic</b>

Frontend part of application is [here](https://github.com/Polt0s/crm-for-clinic)

### Features
* Register users with DOCTOR or NURSE roles. To confirm registration you should go through the link that will be sent to your email address
* Log in to your account
* Restore your password. To confirm password restoration you also have to follow the confirmation link that will be sent to your email address
* Some validation: you should use correct email address, your password should contain at least one digit, uppercase and lowercase letters, you can't register two users with the same email.

### Installing
* Clone repository
* Install docker
* Open command line and change your directory to the root of project
* Use command `docker build -t medical-clinic-backend -f Dockerfile .` to create docker image

### Building and Starting
* Open command line and change your directory to the root of project
* Use command `docker-compose up -d` to start an application. You can see the swagger on address http://localhost:8080/swagger-ui.html

To stop an application use command `docker ps` to see docker containers. Find container with a name `medical-clinic-backend`, copy its id and use command `docker stop 18f58c2a6b52` where `18f58c2a6b52` - id of your container

## Authors

* **Alexey Korovko**

## License

This project is licensed under the MIT License - 
see the [Information about license](https://fr.wikipedia.org/wiki/Licence_MIT) file for details.DO: shared everything else
