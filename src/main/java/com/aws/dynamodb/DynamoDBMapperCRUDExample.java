package com.aws.dynamodb;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

public class DynamoDBMapperCRUDExample {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    public static void main(String[] args) throws IOException {
        testCRUDOperations();
        System.out.println("Example complete!");
    }

    @DynamoDBTable(tableName = "customer")
    public static class CatalogItem {
        private Integer id;
        private String name;

        // Partition key
        @DynamoDBHashKey(attributeName = "cust_id")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @DynamoDBAttribute(attributeName = "cust_name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Customer [ID=" + id + ", Name=" + name + "]";
        }
    }

    private static void testCRUDOperations() {

        CatalogItem item = new CatalogItem();
        item.setId(601);
        item.setName("Book 601");

        // Save the item (book).
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(item);

        // Retrieve the item.
        CatalogItem itemRetrieved = mapper.load(CatalogItem.class, 601);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);

        // Update the item.
        itemRetrieved.setName("Updated Name");
        mapper.save(itemRetrieved);
        System.out.println("Item updated:");
        System.out.println(itemRetrieved);

        // Retrieve the updated item.
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        CatalogItem updatedItem = mapper.load(CatalogItem.class, 601, config);
        System.out.println("Retrieved the previously updated item:");
        System.out.println(updatedItem);

        // Delete the item.
        mapper.delete(updatedItem);

        // Try to retrieve deleted item.
        CatalogItem deletedItem = mapper.load(CatalogItem.class, updatedItem.getId(), config);
        if (deletedItem == null) {
            System.out.println("Done - Sample item is deleted.");
        }
    }
}

