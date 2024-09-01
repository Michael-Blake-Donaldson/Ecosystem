import java.util.List;

public class Organism {
    private double size;
    private double speed;
    private double senseRadius;
    private double energy;
    private double x;
    private double y;
    private boolean hasEaten;
    private long timeOfEating;

    public Organism(double size, double speed, double senseRadius, double safeZoneX, double safeZoneY, double safeZoneWidth, double safeZoneHeight) {
        this.size = size;
        this.speed = speed;
        this.senseRadius = senseRadius;
        this.energy = 100;  // Start with a default energy level
        this.x = safeZoneX + Math.random() * safeZoneWidth; // Start within the safe zone
        this.y = safeZoneY + Math.random() * safeZoneHeight; // Start within the safe zone
        this.hasEaten = false;
    }

    public double getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public double getSenseRadius() {
        return senseRadius;
    }

    public double getEnergy() {
        return energy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public long getTimeOfEating() {
        return timeOfEating;
    }

    public void moveTowards(double targetX, double targetY) {
        double deltaX = targetX - this.x;
        double deltaY = targetY - this.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            this.x += (deltaX / distance) * this.speed;
            this.y += (deltaY / distance) * this.speed;
        }

        // Debugging Output
        System.out.println("Moving towards: (" + targetX + ", " + targetY + ") | New position: (" + x + ", " + y + ")");
    }

    public void seekFood(List<Food> foodSources) {
        Food closestFood = null;
        double closestDistance = Double.MAX_VALUE;

        for (Food food : foodSources) {
            double distance = Math.sqrt(Math.pow(food.getX() - this.x, 2) + Math.pow(food.getY() - this.y, 2));
            if (distance < closestDistance) {
                closestDistance = distance;
                closestFood = food;
            }
        }

        if (closestFood != null && closestDistance <= this.senseRadius) {
            moveTowards(closestFood.getX(), closestFood.getY());
        }
    }

    public boolean eat(Food food) {
        double distance = Math.sqrt(Math.pow(food.getX() - this.x, 2) + Math.pow(food.getY() - this.y, 2));
        if (distance < this.size) {
            this.energy += 50;  // Gain energy
            this.hasEaten = true;  // Mark that the organism has eaten
            this.timeOfEating = System.currentTimeMillis();  // Record the time of eating
            return true;  // Food has been eaten
        }
        return false;
    }

    public void depositEnergy() {
        this.energy = 100;  // Reset energy to maximum after returning to safe zone
        this.hasEaten = false;  // Reset eating status
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
}



