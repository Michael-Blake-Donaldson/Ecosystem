import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Environment {
    private List<Organism> organisms;
    private List<Food> foodSources;
    private double safeZoneX;
    private double safeZoneY;
    private double safeZoneWidth;
    private double safeZoneHeight;

    public Environment() {
        organisms = new ArrayList<>();
        foodSources = new ArrayList<>();
        safeZoneX = 0;
        safeZoneY = 500;  // Safe zone at the bottom of the screen
        safeZoneWidth = 800;
        safeZoneHeight = 100;
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void addFood(Food food) {
        foodSources.add(food);
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public List<Food> getFoodSources() {
        return foodSources;
    }

    public void simulateStep() {
        for (Iterator<Organism> iterator = organisms.iterator(); iterator.hasNext(); ) {
            Organism organism = iterator.next();

            if (organism.hasEaten()) {
                // Check if the organism returns to the safe zone within 10 seconds
                if (System.currentTimeMillis() - organism.getTimeOfEating() > 10000) {  // 10 seconds = 10000 milliseconds
                    iterator.remove();  // Organism dies if it doesn't return in time
                    continue;
                }

                // Move towards safe zone if the organism has eaten
                organism.moveTowards(safeZoneX + safeZoneWidth / 2, safeZoneY + safeZoneHeight / 2);
                if (isInSafeZone(organism)) {
                    organism.depositEnergy();  // Deposit energy and reset eating status
                }
            } else {
                // Seek the closest food
                organism.seekFood(foodSources);

                for (Iterator<Food> foodIterator = foodSources.iterator(); foodIterator.hasNext(); ) {
                    Food food = foodIterator.next();
                    if (organism.eat(food)) {
                        foodIterator.remove();  // Remove food if eaten
                        break;
                    }
                }
            }

            // Decrease energy and remove organism if it runs out of energy
            organism.setEnergy(organism.getEnergy() - 1);
            if (organism.getEnergy() <= 0) {
                iterator.remove();  // Organism dies if it runs out of energy
            }
        }
    }

    private boolean isInSafeZone(Organism organism) {
        return organism.getX() >= safeZoneX &&
               organism.getX() <= safeZoneX + safeZoneWidth &&
               organism.getY() >= safeZoneY &&
               organism.getY() <= safeZoneY + safeZoneHeight;
    }

    public double getSafeZoneX() {
        return safeZoneX;
    }

    public double getSafeZoneY() {
        return safeZoneY;
    }

    public double getSafeZoneWidth() {
        return safeZoneWidth;
    }

    public double getSafeZoneHeight() {
        return safeZoneHeight;
    }
}
