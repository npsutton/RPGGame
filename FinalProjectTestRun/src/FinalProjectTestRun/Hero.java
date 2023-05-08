package FinalProjectTestRun;

import java.util.Random;

/**
 * A class with a Hero constructor that extends the Creature class.
 * This class is the Creature controlled by the user.
 */
public class Hero extends Creature
{
    private static final int HERO_MAX_HP_POOL = 150;
    private static final int HERO_MIN_HP_POOL = 80;
    private static final int HERO_HP_RANGE = HERO_MAX_HP_POOL - HERO_MIN_HP_POOL;
    
    private static final int HERO_MAX_STR_POOL = 10;
    private static final int HERO_MIN_STR_POOL = 2;
    private static final int HERO_STR_RANGE = HERO_MAX_STR_POOL - HERO_MIN_STR_POOL;
    
    private static final int HERO_MAX_SP_POOL = 25;
    private static final int HERO_MIN_SP_POOL = 10;
    private static final int HERO_SP_RANGE = HERO_MAX_SP_POOL - HERO_MIN_SP_POOL;
    
    private static final Random RANDOM_STAT_GENERATOR = new Random();
    
    private static final int GENERATED_HP = RANDOM_STAT_GENERATOR.nextInt(HERO_HP_RANGE + 1);//+1 so it can actually reach the Max range
    private static final int HERO_HP = GENERATED_HP + HERO_MIN_HP_POOL;
    
    private static final int GENERATED_STR = RANDOM_STAT_GENERATOR.nextInt(HERO_STR_RANGE + 1);//+1 so it can actually reach the Max range
    private static final int HERO_STR = GENERATED_STR + HERO_MIN_STR_POOL;
    
    private static final int GENERATED_SP = RANDOM_STAT_GENERATOR.nextInt(HERO_SP_RANGE + 1);//+1 so it can actually reacn the Max range
    private static final int HERO_SP = GENERATED_SP + HERO_MIN_SP_POOL;
    
    public Hero(int startingHP, int startingSTR, int startingSP, boolean turn, boolean defending, String startingStatus)
    {
        super(startingHP, startingSTR, startingSP, turn, defending, startingStatus); 
        
        /*
        Coding the randomly generated stats directly into the constructor, so as
        to make manipulating the Hero's stats more difficult.
        Not fool-proof by any means, but adds an extra level of difficulty
        to any tampering that might occur.
        */
        HP = HERO_HP;
        maxHP = HERO_HP;
        
        STR = HERO_STR;
        
        SP = HERO_SP;
        maxSP = HERO_SP;
        
        isMyTurn = true;
        isDefending = false;
        
        status = "Healthy";
    }
    
    /**
     * Skill for the Hero
     * Causes the Hero to gain a semi-random amount of HP
     * Cost: 2 SP.
     * @param enemy the Creature the Hero is currently fighting
     */
    public void healingSpray(Creature enemy)
    {
        int healthBeforeSpray = this.getHP();
        int minimumHealthRecovered = 10;
        int maximumHealthRecovered = 50;
        int healthRecoveryRange = maximumHealthRecovered - minimumHealthRecovered;
        Random healthRecovered = new Random();
        
        int generatedAmountHealed = healthRecovered.nextInt(healthRecoveryRange + 1);
        int amountHealed = generatedAmountHealed + minimumHealthRecovered;
        
        int healthAfterSpray = healthBeforeSpray + amountHealed;
        
        if(healthAfterSpray >= maxHP)
        {
            int newHP = this.getMaxHP();
            this.HP = newHP;
        }
        else if(healthAfterSpray < maxHP)
        {
            this.HP = healthAfterSpray;
        }
        
        this.SP = this.getSP() - 2;
        
        this.isMyTurn = false;
        enemy.isMyTurn = true;
        
        this.checkStatus();
    }
    
    /**
     * Skill for the Hero
     * The Hero takes 2 strikes with their weapon
     * If the Hero is poisoned, they will take double the poison damage
     * Cost: 3 SP.
     * @param target. The Creature the Hero is attacking.
     */
    public void doubleStrike(Creature target)
    {
        boolean targetDefensePosition = target.isDefending;
        this.attack(target);
        
        target.isMyTurn = false; //To prevent the opposing Creature from doing anything in the middle of the attack.
        this.isMyTurn = true;
        
        target.isDefending = targetDefensePosition; //To ensure that the target's defense remains through the entire attack.
        
        this.attack(target);
        
        this.SP = this.getSP() - 3;
    }
    
    /**
     * Skill for the Hero
     * Displays the Monster's stats
     * Cost: 0 SP (This is to compensate for not showing what the Monster is doing on its turn)
     * @param target. The target Monster
     * @return monsterStats
     */
    public String scan(Creature target)
    {
        String monsterStats = target.getStats();
        return monsterStats;
    }
    
    /**
     * Skill for the Hero
     * Slices the Monster with a poisoned coated blade
     * Target may become poisoned
     * Poison damage takes effect next turn
     * Cost: 1 SP.
     * @param target. The target Monster 
     */
    public void toxicSlash(Creature target)
    {
        Random poisonGenerator = new Random();
        double poisonPossibility = poisonGenerator.nextDouble();
        
        this.attack(target);
        
        if(poisonPossibility <= POISON_CHANCE)
        {
            target.status = "Poisoned";
        }
        
        this.SP = this.getSP() - 1;
    }
}
