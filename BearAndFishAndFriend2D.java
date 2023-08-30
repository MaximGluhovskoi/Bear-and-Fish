import java.util.Arrays;
public class BearAndFishAndFriend2D
{
   public static void main(String[] args)
   {
      Ecosystem2D maximsBackyard = new Ecosystem2D(10, 100);
      System.out.println(maximsBackyard);
      System.out.println("Bears: " + maximsBackyard.animalCount("Bear"));
      System.out.println("Fish: " + maximsBackyard.animalCount("Fish"));
      System.out.println();
      for (int i = 1; i <= 100; i++)
      {
         System.out.println(maximsBackyard.next());
         System.out.println("Bears: " + maximsBackyard.animalCount("Bear"));
         System.out.println("Fish: " + maximsBackyard.animalCount("Fish"));
         System.out.println();
      }
   }
}

class Ecosystem2D
{
   private Animal[][] river;
   private int bearNum;
   private int fishNum;
   private String emptyChar = "-";
   
   public Ecosystem2D(int rows, int cols)
   {
      river = new Animal[rows][cols];
      bearNum = rows*cols/20;
      fishNum = rows*cols/5;
      initialize();
   }
   
   public Ecosystem2D(int rows, int cols, int bear, int fish)
   {
      river = new Animal[rows][cols];
      bearNum = bear;
      fishNum = fish;
      initialize();
   }
   
   private void initialize()
   {
      //generates amount of fish specified by user in random spaces
      for (int i = 0; i < fishNum; i++)
      {
         int r = (int) (Math.random()*river.length);
         int c = (int) (Math.random()*river[0].length);
         while (river[r][c] != null)
         r = (int) (Math.random()*river.length);
         c = (int) (Math.random()*river[0].length);
         river[r][c] = new Fish();
      }
      
      //generates amount of bears specified by user in random spaces
      for (int i = 0; i < bearNum; i++)
      {
         int r = (int) (Math.random()*river.length);
         int c = (int) (Math.random()*river[0].length);
         while (river[r][c] != null)
         r = (int) (Math.random()*river.length);
         c = (int) (Math.random()*river[0].length);
         river[r][c] = new Bear();
      }
   }
   
   public String next()
   {
      for (Animal[] arr : river)
         for (Animal a : arr)
            if (a != null)
               a.unmoved();
      for (int r = 0; r < river.length; r++)
      {
         for (int c = 0; c < river[r].length; c++)
         {
            int xPos;
            int yPos;
            if ((river[r][c] != null) && (river[r][c].getPower() > 0) && (!(river[r][c].getMoved())) )
            {
               river[r][c].moved();
               //randomly decides to move back or forward one space on x and y axis
               xPos = ((int) (Math.random() * 4 - 2));
               yPos = ((int) (Math.random() * 4 - 2));
               while ( (r+yPos < 0) || (r+yPos >= river.length))
                  yPos = ((int) (Math.random() * 4 - 2));
               while ( (c+xPos < 0) || (c+xPos >= river[r].length))
                  xPos = ((int) (Math.random() * 4 - 2));
               //checks if space is moveable then moves
               if ( (xPos == 0) && (yPos==0))
               {
               
               }
               else if (river[r+yPos][c+xPos] == null)
               {
                  river[r+yPos][c+xPos] = river[r][c];
                  river[r][c] = null;
               }
               //checks if animals are same animal and then spawns baby
               else if (river[r][c].getAnimal().equals(river[r+yPos][c+xPos].getAnimal()))
               {
                  spawnAnimal(river[r][c]);
               }
               //if animal is more powerful than where it moves, animal where it moves dies
               else if (river[r][c].getPower() > river[r+yPos][c+xPos].getPower())
               {
                  river[r+yPos][c+xPos] = river[r][c];
                  river[r][c] = null;
               }
               //kills weaker animal if it moves on stronger
               else if (river[r][c].getPower() < river[r+yPos][c+xPos].getPower())
               {
                  river[r][c] = null;
               }
            }
         }
      }
      
      return toString();
   }
   
   //creates new animal at random location if river is not full
   private void spawnAnimal(Animal a)
   {
     /** int r = ((int) (Math.random() * river.length));
      int c = ((int) (Math.random() * river[0].length));
      if (!(isFull()))
      {
         while (river[r][c] != null)
         {
            r = ((int) (Math.random() * river.length));
            c = ((int) (Math.random() * river[0].length));
         }
         switch(a.getAnimal())
         {
            case "Bear":
               river[r][c] = new Bear();
               break;
            case "Fish":
               river[r][c] = new Fish();
               break;
         }
      }**/
   }
   
   //checks if there are any empty spaces left in river
   private boolean isFull()
   {
      boolean full = true;
      for (int r = 0; r < river.length; r++)
         for (int c = 0; c < river[r].length; c++)
            if (river[r][c] == null)
               full = false;
      return full;
   }
   
   public int animalCount(String type)
   {
      int count = 0;
      switch(type)
      {
         case "Bear":
            for (Animal[] arr : river)
               for (Animal a : arr)
                  if (a != null)
                     if (a.getAnimal().equals("Bear"))
                        count++;
            break;
         case "Fish":
            for (Animal[] arr : river)
               for (Animal a : arr)
                  if (a != null)
                     if (a.getAnimal().equals("Fish"))
                        count++;
            break;
      }
      return count;
   }
   
   public String toString()
   {
      String output = "";
      for (int r = 0; r < river.length; r++)
      {
         for (int c = 0; c < river[r].length; c++)
         {
            if (river[r][c] != null)
               output += river[r][c].getChar();
            else
               output += emptyChar;
         }
         output += "\n";
      }
      
      return output;
   }
   
}