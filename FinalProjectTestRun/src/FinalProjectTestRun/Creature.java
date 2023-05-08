package FinalProjectTestRun;

/**
 * Superclass that creates a Creature with all basic stats and
 * actions such as attack.
 */
public class Creature 
{
    protected final double ATTACK_MODIFIER = 1;
    protected final double POISON_CHANCE = 0.2; 
    
    protected int HP;
    protected int maxHP;
    protected int STR;
    protected int SP;
    protected int maxSP;
    
    protected boolean isAttacked;
    protected boolean isMyTurn;
    protected boolean isDefending = false;
    
    protected String status = "Healthy";
    
    public Creature(int startingHP, int startingSTR, int startingSP, boolean turn, boolean defending, String startingStatus)
    {
        HP = startingHP;
        maxHP = startingHP;
        STR = startingSTR;
        SP = startingSP;
        maxSP = startingSP;
        
        
        isMyTurn = turn;
        isDefending = defending;
        
        status = startingStatus;
    }
    
    /**
     * Accessor method that returns the target Creature's
     * current HP value
     * @return HP
     */
    public int getHP()
    {
        return HP;
    }
    
    /**
     * Accessor method that returns the target Creature's
     * maximum HP value
     * @return maxHP
     */
    public int getMaxHP()
    {
        return this.maxHP;
    }
    
    /**
     * Accessor method that returns the target
     * Creature's STR value
     * @return STR
     */
    public int getSTR()
    {
        return STR;
    }
    
    /**
     * Accessor method that returns the target
     * Creature's current SP value
     * @return SP
     */
    public int getSP()
    {
        return this.SP;
    }
    
    /**
     * Accessor method that returns the target
     * Creature's max SP value
     * @return maxSP
     */
    public int getMaxSP()
    {
        return this.maxSP;
    }
    
    /**
     * Accessor method that returns the target
     * Creature's current status
     * @return status
     */
    public String getStatus()
    {
        return this.status;
    }
    
    /**
     * Method that results in the Creature calling the method to
     * attack the target Creature
     * @param target 
     */
    public void attack(Creature target)
    {
        target.takeAttackDamage(this);
    }
    
    /**
     * Method that calculates the damaged taken by
     * a Creature that has been attacked.
     * @param attacker 
     */
    public void takeAttackDamage(Creature attacker)
    {
        int remainingHP;
        double baseAttackDamage;
        int actualAttackDamage;
        
        if(this.isDefending == true)
        {
            baseAttackDamage = attacker.getSTR() * ATTACK_MODIFIER;
            actualAttackDamage = (int) baseAttackDamage;
            
            int actualDamage = (int) baseAttackDamage/2;
            
            if(actualDamage <= 0)
            {
                actualDamage = 1;
            }
            
            remainingHP = this.getHP() - actualDamage;
            
            this.HP = remainingHP;
            this.isDefending = false;
        }
        else if(this.isDefending == false)
        {
            baseAttackDamage = attacker.getSTR() * ATTACK_MODIFIER;
            actualAttackDamage = (int) baseAttackDamage;
        
            remainingHP = this.getHP() - actualAttackDamage;
        
            this.HP = remainingHP;
        }
        
        this.isMyTurn = true;
        attacker.isMyTurn = false;
        
        this.deathCheck();
        this.checkStatus();
    }
    
    /**
     * Method that allows a Creature to defend against an attack.
     * It causes the Creature's HP value to temporarily be doubled
     * until they next take damage, it then reverts to its normal
     * max value.
     */
    public void defend(Creature attacker)
    {   
        this.isDefending = true;
        this.isMyTurn = false;
        attacker.isMyTurn = true;
        this.checkStatus();
    }
    
    /**
     * Method that checks the target Creature's current
     * HP value to see if the Creature is dead or alive
     */
    public void deathCheck()
    {
        if(this.HP <= 0)
        {
            this.status = "Dead";
        }
    }
    
    /**
     * Method that checks the target Creature's current
     * status and makes changes accordingly
     */
    public void checkStatus()
    {
        if(this.status.equals("Healthy"))
        {
            //Nothing changes
        }
        else if(this.status.equals("Poisoned"))
        {
            if(this.isMyTurn == false)
            {
                //While poisoned, the Creature loses a percentage of health each turn
                int currentHealth = this.getHP();
                int poisonDamageModifier = 20;
                
                int poisonDamage = this.getMaxHP()/poisonDamageModifier;
                int healthAfterPoison = currentHealth - poisonDamage;
                
                this.HP = healthAfterPoison;
                this.deathCheck();
            }
        }
        if(this.status.equals("Dead"))
        {
            this.isMyTurn = false;
        }
    }
    
    /**
     * Method that shows a Creature's stats
     * @return showStats
     */
    public String getStats()
    {
        String showHP = "HP =\t" + this.getHP() + "/" + this.getMaxHP() ;
        String showSP = "SP =\t" + this.getSP() + "/" + this.getMaxSP();
        String showStats = showHP + "\n " + showSP;
        return showStats;
        
        //The offset of the Status, while initially unintentional, will remain as I find that it makes it stand out more
        //and isn't that displeasing to look at.
    }
}
