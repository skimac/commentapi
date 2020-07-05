###### Installation
1) Install **docker** & **docker-compose** if needed
2) From main project directory, execute following command:
    - **docker-compose up**
    
###### Usage

- Creating new comment
    - POST  localhost:9000/comments
        with JSON body:
        {
            "content": "Example comment"
        }
- Deleting comment:
    - DELETE localhost:9000/comments/{id},
        where {id} is path variable with id
        of comment to delete
- Updating comment:
    - PUT localhost:9000/comments/{id}
        with JSON body:
        {
            "content":  "Updated comment"
        }
        , where {id} is path variable with id
         of comment to update
- Listing comments:
    - GET localhost:9000/comments?order=natural&filter=xyz&prefix=abc,
        where all query parameters are optional.
    - Parameters description:
        1) **order** - used for ordering comments lexicographically
         by their content. It can be either 'natural' or 'reverse'.
        2) **filter** - used for to filter comments by those that contain
         a string (nevermind in uppercase or lowercase).
        3) **prefix** - a prefix which will be added to every listed comment.