# Steps to setup the project and execute
- 1.Install and setup Mysql on local and create a database say - "gst_billing_system"
- 2.clone the repository
- 3.Open any IDE(IntelliJ or STS) and select open project.Select the folder backend.
- 4.Make sure Java and Maven are installed on the machine.
- 5.In application.properties do below
    - replace {your_database_name} by the name of the database created in step 2
    - replace {your_password} by the password added while setting up mysql
    - replace {your_secret_key} by any of your choice say - charan3972
    - replace {your_admin_password} by any of your choice say - SaiCharan@8688011464
    - replace {your_frontend_base_url} by any of your choice say - http://localhost:3000
- 6.Open terminal , go to directory backend
- 7.Run the command - mvn clean install
- 8.Post successful build, run the main file - GSTBillingApplication , server starts on port 8085
- 9.Open VS code
- 10.select folder frontend
- 11.Open terminal and run the command - http-server -p 3000 , server starts on port 3000
- 12.Open http://localhost:3000/index.html
- 13.Now we can login as admin or user and do the operations.
- 14.To login as user , first login as admin and add user and then use those creds to login as user.
- 15.Admin can add user,add category,add product,view sales,view revenue
- 16.User can only record sale and view bill

# tables with sample data
## users
<table border="1">
  <thead>
    <tr>
      <th>id</th>
      <th>password</th>
      <th>username</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>$2a$10$qYqmZadpjXFL2B0cQxUOXO/h0.RQa3UL8Y3ldS.RFRi9diLVrDTWa</td>
      <td>admin</td>
    </tr>
    <tr>
      <td>2</td>
      <td>$2a$10$Std4mq3CxxRuztBLB90vmeCZ46fVH62h5dqSJyKOdBw/6KQqz0Ow2</td>
      <td>user1</td>
    </tr>
    <tr>
      <td>3</td>
      <td>$2a$10$SDb8gPE3REX07QcxXlxds.Vxh.rNzGjcZSfHgdKMtJY.XWtL9rhwa</td>
      <td>user3</td>
    </tr>
    <tr>
      <td>4</td>
      <td>$2a$10$5KmZ4ORhyk0nkg7TzlgnBOpcyi2Vhfv80Wz0pUF0sFj6A6ziO3S..</td>
      <td>user2</td>
    </tr>
    <tr>
      <td>5</td>
      <td>$2a$10$BE2uZRsEdgBXLjQJSc2p/ejBrnqDL65XADLW5L/AyzNzCz3Vi0A/C</td>
      <td>user4</td>
    </tr>
  </tbody>
</table>


- user_roles
- +---------+-------+
- | user_id | role  |
- +---------+-------+
- |       1 | ADMIN |
- |       2 | USER  |
- |       3 | USER  |
- |       4 | USER  |
- |       5 | USER  |
- +---------+-------+

- product_category
- +----+----------+-------------+
- | id | gst_rate | name        |
- +----+----------+-------------+
- |  1 |        5 | Food        |
- |  2 |       10 | Footwear    |
- |  3 |       20 | Electronics |
- +----+----------+-------------+

- product
- +----+---------+-------+-------------+
- | id | name    | price | category_id |
- +----+---------+-------+-------------+
- |  1 | Mobile  | 20000 |           3 |
- |  2 | Laptop  | 50000 |           3 |
- |  3 | 1kgRice |    50 |           1 |
- |  4 | Sandals |  1000 |           2 |
- +----+---------+-------+-------------+

- sale
- +----+------------+------------+--------------+
- | id | sale_date  | tax_amount | total_amount |
- +----+------------+------------+--------------+
- |  1 | 2024-09-09 |    24407.5 |     148557.5 |
- |  2 | 2024-09-09 |      14000 |        84000 |
- |  3 | 2024-09-09 |       8000 |        48000 |
- |  4 | 2024-09-09 |       8000 |        48000 |
- |  5 | 2024-09-09 |       8300 |        51300 |
- |  6 | 2024-09-09 |     4007.5 |      24157.5 |
- |  7 | 2024-09-09 |      20000 |       120000 |
- |  8 | 2024-09-09 |      28000 |       168000 |
- +----+------------+------------+--------------+

- sale_item
- +----+----------+------------+--------------+------------+---------+
- | id | quantity | tax_amount | total_amount | product_id | sale_id |
- +----+----------+------------+--------------+------------+---------+
- |  1 |        1 |       4000 |        24000 |          1 |       1 |
- |  2 |        2 |      20000 |       120000 |          2 |       1 |
- |  3 |        3 |        7.5 |        157.5 |          3 |       1 |
- |  4 |        4 |        400 |         4400 |          4 |       1 |
- |  5 |        1 |       4000 |        24000 |          1 |       2 |
- |  6 |        1 |      10000 |        60000 |          2 |       2 |
- |  7 |        2 |       8000 |        48000 |          1 |       3 |
- |  8 |        2 |       8000 |        48000 |          1 |       4 |
- |  9 |        2 |       8000 |        48000 |          1 |       5 |
- | 10 |        3 |        300 |         3300 |          4 |       5 |
- | 11 |        1 |       4000 |        24000 |          1 |       6 |
- | 12 |        3 |        7.5 |        157.5 |          3 |       6 |
- | 13 |        2 |      20000 |       120000 |          2 |       7 |
- | 14 |        2 |       8000 |        48000 |          1 |       8 |
- | 15 |        2 |      20000 |       120000 |          2 |       8 |
- +----+----------+------------+--------------+------------+---------+

# UI images

### Login Page
![Admin Login](UIScreenshots/admin-login.png)
![User Login](UIScreenshots/user-login.png)

### Admin Dashboard
![Admin Dashboard](UIScreenshots/admin-dashboard.png)

### Add User
![Add User](UIScreenshots/add-user.png)

### Add Category
![Add Category](UIScreenshots/add-category.png)

### Add Product
![Add Product](UIScreenshots/add-product.png)

### View Sales
![View Sales](UIScreenshots/view-sales.png)

### View Revenue
![View Revenue](UIScreenshots/view-revenue.png)

### User Dashboard
![User Dashboard](UIScreenshots/user-dashboard.png)

### Record Sale
![Record Sale](UIScreenshots/record-sale.png)

### View Bill
![View Bill](UIScreenshots/view-bill.png)

### Logging out
![Logging out](UIScreenshots/logging-out.png)







