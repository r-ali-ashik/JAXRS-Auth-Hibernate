Required step(s) to successfully run the project

- Run the database script (check mysql_script.sql file). Which will create and populate required tables.

- We have following resource end point: 
    
    http://localhost:8080/users/login (Use for login purpose, it will create JWT token)
    
    http://localhost:8080/api/echo (Public, no authentication is required)
    
    http://localhost:8080/api/echo/jwt (JWT token required)

- To access secured resource use one of the following payloads to and hit http://localhost:8080/users/login. 

    {
        "username" : "admin",
        "password" : "admin"
    }
    
    {
        "username" : "user",
        "password" : "user"
    }
    
    {
        "username" : "client",
        "password" : "client"
    }

- Server will return a JWT token in Authorization header 

- Now hit the http://localhost:8080/api/echo/jwt and add the JWT token with Authorization header

- Server will return corresponding response. 