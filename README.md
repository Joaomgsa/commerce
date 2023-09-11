# MyCommerce

Uma API em Java e Spring Framework , Spring JPA + Postges de uma loja virtual.

## AUTHENTICATION
    Authorization: Basic Auth
    
        Application 
        endpoint http://localhost:8080/oauth/token
        Method: Post
        Username: clientid
        Password: clientsecret

        Body:
        username: mail@domain.com
        password: password
        grant_type: password

## ENDPOINTS

    /orders
        /{id}  method: GET (Order by id) 
        method: POST (Create new order) 

    /products

            /  method: GET (Products)   
                    Query Parameters
                    size: int
                    page: int
                    sort: (name,desc) string
                    name: (pc%20gamer) string

            /{id}  method: GET (Product by id)
        