import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        List<Product> products = asList(
                new Product(1, "The witcher", 250, ProductType.BOOKS, false, LocalDate.of(2022, Month.NOVEMBER, 3)),
                new Product(2, "bread", 5, ProductType.FOOD, true, LocalDate.of(2022, Month.NOVEMBER, 4)),
                new Product(3, "butter", 10, ProductType.FOOD, true, LocalDate.of(2022, Month.NOVEMBER, 5)),
                new Product(4, "Lord of the rings", 300, ProductType.BOOKS, true, LocalDate.of(2011, Month.NOVEMBER, 6)),
                new Product(5, "bijouterie", 15, ProductType.TRINKETS, true, LocalDate.of(2022, Month.NOVEMBER, 7)),
                new Product(6, "Cheap Book Detective", 30, ProductType.BOOKS, true, LocalDate.of(2010, Month.NOVEMBER, 6)),
                new Product(7, "Cheap Book Romantic", 30, ProductType.BOOKS, true, LocalDate.of(2022, Month.NOVEMBER, 6)),
                new Product(8, "Cheap Book Fantasy", 30, ProductType.BOOKS, true, LocalDate.of(2022, Month.NOVEMBER, 6))
        );
        System.out.println(filterByPrice(products, 250));

        System.out.println(basket(products, true));
        try {
            System.out.println(findChepest(products, ProductType.BOOKS));
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(getLastProductsAdded(products, 3));
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(calculateTotalCost(products, LocalDate.ofYearDay(2022, 1), ProductType.BOOKS, 75));
        System.out.println(groupByType(products));
    }

    private static List<Product> filterByPrice(List<Product> products, double price) {
        return products.stream()
                .filter(product -> product.getType() == ProductType.BOOKS)
                .filter(product -> product.getPrice() >= price)
                .toList();
    }

    public static List<Product> basket(List<Product> products, boolean isDiscounted){
        return products.stream()
                .filter(product -> product.getType() == ProductType.BOOKS)
                .peek(product -> {
                    if (isDiscounted) {
                        product.applyDiscoutn(0.9);
                    }
                }).collect(Collectors.toList());
    }

    public static Product findChepest(List<Product> products, ProductType productType) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> product.getType() == productType)
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new ProductNotFoundException("Product [" + productType + "] not found"));
    }

    public static List<Product> getLastProductsAdded(List<Product> products, int count){
        return products.stream()
                .sorted(Comparator.comparing(Product::getCreatedAt).reversed())
                .limit(count)
                .toList();
    }

    public static double calculateTotalCost(List<Product> products, LocalDate dateOfCreation, ProductType type, double price){
        return products.stream()
                .filter(product -> product.getCreatedAt().getYear() == dateOfCreation.getYear())
                .filter(product -> product.getType() == type)
                .map(Product::getPrice)
                .filter(productPrice -> productPrice <= price)
                .reduce(Double::sum).orElse(0.);
    }

    public static Map<ProductType, List<Product> > groupByType(List<Product> products){
        return products.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }

}