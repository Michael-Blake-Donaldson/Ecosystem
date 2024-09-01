# Project Overview
### This project is an interactive ecosystem simulation where virtual organisms, or "blobs," compete for survival in a simulated environment. The organisms must navigate the environment, seek out food, and return to a safe zone before their energy runs out. The simulation is designed to explore basic principles of natural selection, energy management, and organism behavior in a simplified ecosystem.

# Purpose
### The primary purpose of this project is to create a simulation that demonstrates key concepts in evolutionary biology, such as survival, reproduction, and natural selection. The project also serves as a practical example of object-oriented programming (OOP) and graphical user interface (GUI) development using Java and JavaFX.

# Key Features
## Organism Behavior:  Each organism starts in a designated safe zone and must find food to survive. After consuming food, the organism must return to the safe zone within a specified time limit.
## Energy Management:  Organisms lose energy over time. They gain energy by consuming food, but if they fail to return to the safe zone before their energy depletes, they die.
## Safe Zone:  A designated area on the screen where organisms must return after finding food to deposit their energy.
## Reproduction (Future Feature):  Organisms may reproduce if they gather enough energy, creating new organisms with slightly mutated traits.
## Natural Selection (Future Feature):  Over time, traits that are advantageous for survival may become more prevalent in the population.

# Technologies Used
## Java:  The core programming language used to implement the simulation logic, organism behavior, and energy management.
## JavaFX:  A GUI toolkit used to create the graphical interface, including the simulation display, controls, and real-time updates.
## Object-Oriented Programming (OOP):  The simulation is structured using OOP principles, with classes representing organisms, the environment, and food items.
## AnimationTimer:  Used to create a continuous simulation loop that updates the positions and states of organisms in real-time.
