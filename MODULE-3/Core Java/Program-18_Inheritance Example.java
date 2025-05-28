// Program 18: Inheritance Example
class Animal {
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }

    public static void main(String[] args) {
        Animal generic = new Animal();
        Dog myDog = new Dog();
        generic.makeSound();
        myDog.makeSound();
    }
}