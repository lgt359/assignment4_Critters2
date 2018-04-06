README.txt
Programming Assignment 5: CritterPart2
---------------------------------------------------------------------------------------------
New classes Implemented(4):

- Critter1.java
NAME: The Straight-Edge
ATTRIBUTES: This critter only fights when it has energy > 50
MOVEMENT: Only moves horizontally/vertically (random)
COLOR/SHAPE: STAR/RED

- Critter2.java
NAME: The Diagonal
ATTRIBUTES: This critter only fights when it has energy > 50
MOVEMENTS: Only moves diagonally (random)
COLOR/SHAPE: DIAMOND/PURPLE

- Critter3.java
NAME: The Gardening Tool
ATTRIBUTES: This critter looks ahead for other critters and runs away if a critter is seen
MOVEMENTS: Always tries to reproduce if possible and never fights.
OFFSPRING POSITION: Offspring always spawns below parent
COLOR/SHAPE: TRIANGLE/ORANGE

- Critter4.java
NAME: The Drunk Rock
ATTRIBUTES: This critter always wants to fight
MOVEMENT: Always runs and charges randomly(unless a critter is seen by looking ahead)
COLOR/SHAPE: CIRCLE/YELLOW

---------------------------------------------------------------------------------------------
Methods/Flags added:

Methods:
-worldInit: Initializes world pane where animation/display occurs.
-makeInit: Initializes all buttons, sliders, and combo box that was used to make critters.
-exitInit: Initializes quit button
-stepInit: Initializes all buttons and sliders to step through a worldTimeStep.
-aniInit: Initializes all buttons, sliders, and combo box that was used to animate world.
-statsInit: Initializes button and textField that we used to display runStats.
-errorInit: Handles all errors by displaying error window.


Flags:
-isAnimating: Boolean flag to exit world animation
-isStatsRunning: Boolean flag to exit stats animation

---------------------------------------------------------------------------------------------
Additional Notes:

We separated our world and controls in two different stages.

We assumed that when animating our world, we can only create more critters and run stats
- All other buttons was disabled while doing this

We assumed that we can only run stats on one critter at a time chosen by the combo box.
