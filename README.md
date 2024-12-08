# The Capstone Project

## The Singleton Design Patterns
![idea64_SNBipwjysa](https://github.com/user-attachments/assets/cb5bab09-019e-407b-833b-7601cf3387ea)

![idea64_IDRvZZ1BEp](https://github.com/user-attachments/assets/a198285d-cc3f-4ea3-8815-669e71dfe169)

![idea64_uqZVdQXrC9](https://github.com/user-attachments/assets/350c6fdf-cf33-417a-a8bd-6b831906e87e)

The more we use the singleton design pattern, the more we realize that its more than a way of making sure that only one instance can only be made during runtime. It makes the methods and fields of the class into a psuedo static class without making them static. Here is an example with the window instance.

![idea64_6c11jwAe2g](https://github.com/user-attachments/assets/cbf3675a-4992-4358-9d85-6051eae14c83)

![idea64_GLIaId8PCs](https://github.com/user-attachments/assets/91b98673-0f24-43e7-b6cf-2d90a8de7318)

Normally you can't use non-static fields in your static methods, but under the hood the currentScene, mouseListener, keyListener are not static fields. How did we circumvent this, but using the getWindow method (our singleton method) as a way to bridge the gap between static methods and non-static fields.

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

In this example we have a menu item, think of it as a button, when the button is clicked this block of code operates. It duplicates the menu item but removes the MenuItem Component, leaving only the Sprite Component as its only Component. We then add the Snap to Grid component and we then swap the current game object as our new mouse cursor, making the sprite from the menu item as our mousecursor. And if we want to change the sprite of the mousecursor all we have to do is click a different menu item. 

What I want to illustrate is that with the ECS we can freely add and remove functionality without the constraints of a tree like structure. Lets say we have an List of playable characters, We can easliy add a playerController component if we need to like wise we can also easily add an AIController if the situation calls for it. 
