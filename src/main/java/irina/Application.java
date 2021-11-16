package irina;

import java.sql.*;

public class Application {
    private static final String DB_URL = "jdbc:postgresql:customer";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "111";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            //соединение с бд
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(
                    "select customer.name, customer.phone, sum(price) as sum_orders\n" +
                            "from customer\n" +
                            "join customer_orders\n" +
                            "on customer.id = customer_id\n" +
                            "join orders\n" +
                            "on order_id = orders.id\n" +
                            "group by customer.name, customer.phone\n" +
                            "order by sum_orders desc\n" +
                            ";"
            );

            //переместить курсор на 1 элемент вниз и перемещается до конца, пока есть, что возвращать
            while (resultSet.next()) {
                System.out.printf("\tcustomer_id - %s%n", resultSet.getString("name"));
                System.out.printf("\tcustomer_id - %s%n", resultSet.getString("phone"));
                System.out.printf("\tsum_orders - %s%n", resultSet.getString("sum_orders"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
