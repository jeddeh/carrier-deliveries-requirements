
Last Modified : 2014-03-15 Rob Grant


Requirement :

  A RESTful web service that accepts a Get Request using Basic Authentication. 
  See also the ReadMe in the 'deliveries Patch Request Requirements' folder.

 


Request :

  Example resource URI pattern : /{version}/deliveries/getAll/{carrier run}

      version     :   The current API version for the web service, initially 'v1'. 

      carrier run :   Min length : 4
                      Max length : 4
                      First character should be an 'A', 'B', 'H', 'M', 'P' or 'S' corresponding with the Branch code.
                      Last three characters should be numeric and correspond to the Run number.
              
  Request Headers :
  
    Accept : application/json
            

    Authorization : The Carrier Run and Distributor Code are combined into a string "Carrier Run:Distributor Code".
                    The resulting string is base64 encoded.
                    The base64 string is prefixed by the string "Basic ".

                    This is in keeping with Basic Access Authentication standards.

                    For example, for Carrier Run S001 and Distributor Code 90241998 the header value will be 
                      Basic UzAwMTo5MDI0MTk5OA==
  
        Distributor Code : Min length : 1
                           Max length : 12
                           All characters should be numeric.
                           This will correspond to the "Distributor Code" for the Carrier Run on EVA.
                    



Reponse :
  
  If the Carrier Run in the URL does not match the Carrier Run in the authorization header, the server should 
    return a 401 response code.

  If the Carrier Run does not exist, the server should return a 401 response code.

  If the Distributor Code for the Carrier Run is not correct, the server should return a 401 response code.

  Otherwise, the server should attempt to respond with a JSON string containing an array of 'Delivery' objects 
    for the current distribution for the Carrier Run.

  A 'Delivery' object represents all the catalogues required to be delivered to a single address for a single
    distribution.

  The 'Delivery' objects should be ordered in the same order as they are routed by the driver on EVA.

  The Content-Type header should be "application/json".

  Double Quotes in JSON values should be escaped by a single backslash.
  Backslashes in JSON values should be escaped by a single backslash.

  Aliases for the keys (eg. "Ad" instead of "Address") are not used in order to improve readability, as this 
    request only needs to be made once by a user (driver) per carrier run per distribution.

  4xx and 5xx response codes should have an error message in the response body.

  Please see the attached sample reponse in this folder.

  

  For the 'Delivery' objects...

  key   : DeliveryId
  value : Required, Numeric, Unquoted, Not Null
          Min value : 1
          Max value : java long MAX_VALUE
          This value is used to identify the Delivery when the driver confirms the catalogues have been delivered 
            via a Patch request to the server. It should be unique to the Delivery.

  key   : Address
  value : Required, String, Quoted, Not Null, Not Empty
          Min length : 1
          Max length : Not specified
          The delivery address.

  key   : IsDelivered
  value : Required, Boolean, UnQuoted, Not Null
          True if the delivery has been completed.          

  key   : DeliveryItems
  value : Required, Array, Not Null
          An array of 'Delivery Item' objects representing the catalogue versions and quantities to be delivered to
            the address.

  For the 'Delivery Item' objects...

  key   : JobId
  value : Required, String, Quoted, Not Null, Not Empty
          Min length : 6
          Max Length : 8
          The Job Id for the catalogue.

  key   : JobName
  value : Required, String, Quoted, Not Null, Not Empty
          Min length : 1
          Max length : Not specified
          The Job Name for the catalogue including overprint.

  key   : Quantity
  value : Required, Numeric, Not Quoted, Not Null
	  Min value : 1
          Max value : Java int MAX_VALUE
          The number of catalogues to be delivered to the address for the catalogue version.

  key   : BundleSize
  value : Required, Numeric, Not Quoted, Not Null
          Min value : 0
          Max value : Java int MAX_VALUE
          The bundle size for the catalogue.
          If the bundle size has not been entered into Phantom, the value should be 0.
