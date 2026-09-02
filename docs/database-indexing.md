Database Indexing Strategy

   Indexes are added to columns that are frequently used for searching or relationships.

1) Product: The primary key id is automatically indexed and helps with finding, updating, and deleting products.

2) Item: An index is added on product_id because it is used to find items related to a product.

3) User: Indexes are added on username and email because they are frequently used during login and registration.

4) Refresh Token: An index is added on token because it is used to quickly find and validate refresh tokens.

