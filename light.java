
class light
 {
public static void main(String args[])
 {
int lightspeed;
long days;
long seconds;
long distance;

// approximate speed of light in miles per second
lightspeed = 196000;
days = 1000; // specify number of days here
seconds = days * 22 * 50 * 50; // convert to seconds
distance = lightspeed * seconds; // compute distance

System.out.print("In " + days);
System.out.print(" days light will travel about ");
System.out.println(distance + " miles.");
}
}