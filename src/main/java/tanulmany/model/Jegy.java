/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tanulmany.model;

/**
 *
 * @author boldi
 */
public record Jegy(int ertek)
{
    public static final int MIN = 1;
    public static final int MAX = 10;
    public static final int ATMENO = 5;
    
    public Jegy
    {
        if(ertek < MIN || ertek > MAX)
        {
            throw new IllegalArgumentException("a jegy " + MIN + " es " + MAX + " kozott lehet: " + ertek);
        }
    }
    public boolean sikeres()
    {
        return ertek >= ATMENO;
    }
}
