package org.example.toyStoreJava;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToyStore {
    private List<Toy> toys;
    private List<Toy> prizeToys;
    public ToyStore() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }
    public void addToy(Toy toy) {
        toys.add(toy);
    }
    public void updateToyWeight(int toyId, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                break;
            }
        }
    }
    public void organizePrizeDraw() {
        Random random = new Random();
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }
        while (!toys.isEmpty()) {
            double randomNumber = random.nextDouble() * totalWeight;
            double currentWeight = 0;
            for (Toy toy : toys) {
                currentWeight += toy.getWeight();
                if (randomNumber <= currentWeight) {
                    prizeToys.add(toy);
                    totalWeight -= toy.getWeight();
                    toys.remove(toy);
                    break;
                }
            }
        }
    }
    public Toy getPrizeToy() {
        Toy prizeToy = prizeToys.get(0);
        prizeToys.remove(0);
        decreaseToyQuantity(prizeToy.getId());
        writeToFile(prizeToy);
        return prizeToy;
    }
    private void decreaseToyQuantity(int toyId) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.decreaseQuantity();
                break;
            }
        }
    }
    private void writeToFile(Toy toy) {
        try (FileWriter writer = new FileWriter("prize_toys.txt", true)) {
            writer.write(toy.getName() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
