# The Capstone Project

## The game loop (run-draw cycle)
Trying to display something on a screen is easy but trying to display something moving in a cohesive way is challenging. One of the ways we can do this is the game loop. Think of the game loop like a flip book. In order to create an animation of a ball jumping using a flip book is that you draw the same ball but with each subsequent page the ball's position is slightly higher if its going up or slight lower if its going down relative to the previous page. And once your done you quickly flip the page in order to play the animation. We can achieve the same result by using the game loop 

### The code
This is how we implement the game loop, in the Window class we create methods for the game loop but we let the scene class to decide on which elements to draw, delta time dictates the speed of the page turn

![image](https://github.com/user-attachments/assets/91156105-e38b-4d02-99aa-2829a7a46c33)
![idea64_SSr1xMIzeQ](https://github.com/user-attachments/assets/b897b5fc-08aa-42ed-b81f-3203bd57d9d5)
![idea64_AfpjU1TDIT](https://github.com/user-attachments/assets/0519451f-8741-4c2d-a8ad-45d6efdef870)

## The decoupling pattern, Entity Component System
Lets say you are in charge of coding the functionality of these games objects.
![msedge_sWTqWdfnoo](https://github.com/user-attachments/assets/87266b88-9984-4003-831e-5841533bba5a)
For these example lets say all items move in a grid based system, some are pushable, some are interactable. So lets say we group these objects togerther in the same class GridObject
![msedge_fu6PyH6ci0](https://github.com/user-attachments/assets/8c03c99a-d3e5-4c2e-b3f1-861559299d52)
And then we can make a child class Pushable for the game objects that have that functionality
![msedge_kM9lIDuKfl](https://github.com/user-attachments/assets/98ed9896-da7b-4f92-94be-985c6e355487)
And also a Interactable chilid class
![msedge_fRE9G3YioV](https://github.com/user-attachments/assets/6593829a-53f3-419b-822f-19f2191b7695)
But what happens if one game object is both interactable and pushable do we maek a PushableInteractable sub class?
![msedge_Bh6BIkeZ8Q](https://github.com/user-attachments/assets/3ea24ce1-3be6-4b21-9d88-30cf2300a5ba)

I know that we can instead of using sub classes we can use interfaces to add the pushable and interactable behavior, but what I hope that this example shows is that sometimes your code's functionality doesn't neatly fit into a tree like structure. This is where the decoupling pattern comes in.

### What is an Entity Component System
The agenda of the decoupling pattern is to utilize composition over inheritance to solve our problems. In our code we have a class called GameObjects, and one of it's fields is a List of Components. Instead of using inheritence we add components to the list. Here is an example:

![idea64_62Vi9Ln8yj](https://github.com/user-attachments/assets/8900f331-2a1a-4287-9b5e-864169b71c97)

The snap to grid component lets a sprite follow a mouse while snapping to a grid

So what is an advantage of the Entity Component System? Not only can we add Components on run time if we wanted to, we can also remove components

![idea64_lgvphpYvZu](https://github.com/user-attachments/assets/9c71a40c-e3e3-4bee-b81e-2667040fb959)

Here is an example of removing components: 

![idea64_6yuC2L7W3R](https://github.com/user-attachments/assets/dc39dfdb-e837-423e-aa55-672ace5e2131)

In this example we have a menu item, think of it as a button, when the button is clicked this block of code operates. It duplicates the menu item but removes the MenuItem Component, leaving only the Sprite Component as its only Component. We then add the Snap to Grid component and we then swap the current game object as our new mouse cursor.

What I want to illustrate is that with the ECS we can freely add and remove functionality without the constraints of a tree like structure. Lets say we have an List of playable characters, We can easliy add a playerController component if we need to like wise we can also easily add an AIController if the situation calls for it.
