
Last Modified : 2014-03-15 Rob Grant


Requirement :

  A RESTful web service that accepts a Patch Request using Basic Authentication. 
  See also the ReadMe in the 'deliveries Get Request Requirements' folder.

 


Request :

  Example resource URI pattern : /{version}/deliveries/{delivery id}

      version     :   The current API version for the web service, initially 'v1'. 

      delivery id :   Min length : 1
                      Max length : 12
                      This is the DeliveryId property of the 'Delivery' object to be updated, 
                        which was sent in the JSON response body of the deliveries Get Request. See the ReadMe
                        in the 'deliveries Get Request Requirements' folder.
              
  Request Headers :

    Content-Type : multipart/form-data

       Authorization    : Same as for the Get Request.

                          The Carrier Run and Distributor Code are combined into a string "Carrier Run:Distributor Code".
                            The resulting string is base64 encoded.
                            The base64 string is prefixed by the string "Basic ".

                            This is in keeping with Basic Access Authentication standards.

                            For example, for Carrier Run S001 and Distributor Code 90241998 the header value will be 
                              Basic UzAwMTo5MDI0MTk5OA==
  
        Distributor Code : Min length : 1
                           Max length : 12
                           All characters should be numeric.
                           This will correspond to the "Distributor Code" for the Carrier Run on EVA.
                    



  Request Body :


    name  : name="timeDelivered"
            Content-Type: text/plain

    value : A timestamp generated on the mobile when the Request was sent.
            This will be a string in the form "yyyy-MM-dd HH:mm:ss"


    name  : name="latitude"
            Content-Type: text/plain

    value : The latitude of the mobile location when the Request was sent.
            If the mobile is unable to get a fix on the location, this value will be empty.
            If the GPS sensor on the mobile is not enabled by the user, this value will be the string "disabled".
            Otherwise, a double data type will be sent.


    name  : name="longitude"
            Content-Type: text/plain

    value : The longitude of the mobile location when the Request was sent.
            If the mobile is unable to get a fix on the location, this value will be empty.
            If the GPS sensor on the mobile is not enabled by the user, this value will be the string "disabled".
            Otherwise, a double data type will be sent.
    

    name  : name="image"
            Content-Type: text/plain

    value : I still need to work on this. It involves some "interesting" problems.

            This value will be empty if an exception is thrown on the mobile when the image is being taken. In this
              case, the deliveries resource should be updated without an image.


            I am planning on sending a JPEG image with the original aspect ratio and a larger dimension of 400px. 
            
            I will attempt to keep and send the EXIF data associated with the image after resizing it.
              This will be needed to determine the image orientation.

            Currenty the application is sending a base64 encoded string containing the JPEG image without EXIF data
              in the Request. This would work for initial testing purposes.

            Sending a image file of "Content-Type: image/jpeg", "Content-Transfer-Encoding: binary" would be a better
              solution.

                       
            Could you contact me about this when you are ready to implement it?



Reponse :

  If the Carrier Run for the Delivery Id does not match the Carrier Run in the authorization header,
    the server should return a 401 response code.

  If the Distributor Code for the Carrier Run is not correct, the server should return a 401 response code.

  If the deliveries resource has already been updated (if a timeDelivered timestamp has been set on the 
    server for the Delivery), the server should return a 409 response code with "Delivery already updated." 
    in the response body.

  If the Delivery is successfully updated, the server should return a 204 response code.

  4xx and 5xx response codes should have an error message in the response body.

  

 