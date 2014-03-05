
/* This interface should include, at the minimum, everything MapView needs
   for rendering a map */
interface IGameMap {
    public int GetRows();
    public int GetColumns();
    public Hex GetHex(int x, int y);
    
    //if 0 first row is like the 1st or 3rd rows in the game map (high or even)
    //if 1 first row is like the 2nd or 4th row in the game map (low or odd)
    public boolean LowFirstRow();
}
