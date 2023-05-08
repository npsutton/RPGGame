package FinalProjectTestRun;

import java.util.Random;

/**
 * A class with a Monster constructor that extends the Creature class.
 * This class contains information specific for the Monster, which is a
 * Creature that is controlled by the computer.
 */
public class Monster extends Creature
{
    //Instance varaibles to determine the range of stat values for the Monster
    private static final int MONSTER_MAX_HP_POOL = 130;
    private static final int MONSTER_MIN_HP_POOL = 75;
    private static final int MONSTER_HP_RANGE = MONSTER_MAX_HP_POOL - MONSTER_MIN_HP_POOL;
    
    private static final int MONSTER_MAX_STR_POOL = 12;
    private static final int MONSTER_MIN_STR_POOL = 2;
    private static final int MONSTER_STR_RANGE = MONSTER_MAX_STR_POOL - MONSTER_MIN_STR_POOL;
    
    private static final int MONSTER_MAX_SP_POOL = 20;
    private static final int MONSTER_MIN_SP_POOL = 8;
    private static final int MONSTER_SP_RANGE = MONSTER_MAX_SP_POOL - MONSTER_MIN_SP_POOL;
    
    //Random number generator to generate the stat values
    private static final Random RANDOM_STAT_GENERATOR = new Random();
    
    //The generation and assigning of said stat values
    private static final int GENERATED_HP = RANDOM_STAT_GENERATOR.nextInt(MONSTER_HP_RANGE + 1);//+1 so it can actually reach the Max range
    private static final int MONSTER_HP = GENERATED_HP + MONSTER_MIN_HP_POOL;
    
    private static final int GENERATED_STR = RANDOM_STAT_GENERATOR.nextInt(MONSTER_STR_RANGE + 1);//+1 so it can actually reach the Max range
    private static final int MONSTER_STR = GENERATED_STR + MONSTER_MIN_STR_POOL;
    
    private static final int GENERATED_SP = RANDOM_STAT_GENERATOR.nextInt(MONSTER_SP_RANGE + 1);//+1 so it can actually reach the Max range
    private static final int MONSTER_SP = GENERATED_SP + MONSTER_MIN_SP_POOL;
    
    public static final String[] monsterActions = 
    {
        "attack", "defend", "poisonSwipe", "furyAttack", "calmingCleanse", "piercingBlow"
    };
    
    public Monster(int startingHP, int startingSTR, int startingSP, boolean turn, boolean defending, String startingStatus)
    {
        super(startingHP, startingSTR, startingSP, turn, defending, startingStatus);
        
        /*
        Coding the randomly generated stat values into the constructor itself
        so that the user can't mess with them and create their own stats.
        Not fool-proof, but adds an extra level of difficulty to tamper with things
        */
        HP = MONSTER_HP;
        maxHP = MONSTER_HP;
        
        STR = MONSTER_STR;
        
        SP = MONSTER_SP;
        maxSP = MONSTER_SP;
        
        isMyTurn = false;
        isDefending = false;
        
        status = "Healthy";
    }
    
    /**
     * A skill method for the Monster
     * The Monster uses its poison coated claws to swipe at the Hero
     * May cause poisoning that will take effect next turn
     * Cost: 2 SP.
     * @param target. The target for the attack
     */
    public void poisonSwipe(Creature target)
    {
        Random poisonChanceCreator = new Random();
        
        this.attack(target);
        
        double poisonPossibility = poisonChanceCreator.nextDouble();
        
        if(poisonPossibility <= POISON_CHANCE)
        {
            target.status = "Poisoned";
        }
        
        this.SP = this.getSP() - 2;
    }
    
    /**
     * Skill for the Monster
     * The Monster becomes enraged in blood lust and takes 2 swings at the Hero
     * If the Monster is poisoned, it will take double the poison damage
     * Cost: 3 SP
     * @param target. The Hero the Monster is attacking
     */
    public void furyAttack(Creature target)
    {
        this.attack(target);
        
        target.isMyTurn = false;
        this.isMyTurn = true;
        
        this.attack(target);
        
        this.SP = this.getSP() - 3;
    }
    
    /**
     * Skill for the Monster
     * The Monster calms down and meditates, causing it to recover some energy
     * and enable it to continue fighting
     * The Monster heals a semi-random amount of health
     * Cost: 2 SP.
     * @param enemy. The Hero the Monster is currently fighting
     */
    public void calmingCleanse(Creature enemy)
    {
        int mostHealthRecovered = 50;
        int leastHealthRecovered = 20;
        int healthRecoveredRange = mostHealthRecovered - leastHealthRecovered;
        int healthBeforeSkill = this.getHP();
        Random healthGenerator = new Random();
        
        int healthGenerated = healthGenerator.nextInt(healthRecoveredRange + 1);
        int healthRecovered = healthGenerated + leastHealthRecovered;
        
        int healthAfterSkill = healthBeforeSkill + healthRecovered;
        
        if(healthAfterSkill >= this.getMaxHP())
        {
            this.HP = this.getMaxHP();
        }
        else if(healthAfterSkill < this.getMaxHP())
        {
            this.HP = healthAfterSkill;
        }
        
        this.isMyTurn = false;
        enemy.isMyTurn = true;
        
        this.SP = this.getSP() - 2;
        
        this.checkStatus();
    }
    
    /**
     * Skill for the Monster
     * The Monster's weapon suddenly gets sharper, thus causing it to deal extra damage
     * The Monster deals a semi-random extra amount of damage
     * Cost: 1 SP
     * @param target. The Hero the Monster is targeting
     */
    public void piercingBlow(Creature target)
    {
        int maxBonusDamage = 5;
        int minBonusDamage = 1;
        int bonusDamageRange = maxBonusDamage - minBonusDamage;
        Random bonusGenerator = new Random();
        
        int generatedDamage = bonusGenerator.nextInt(bonusDamageRange + 1);
        int bonusDamage = generatedDamage + minBonusDamage;
        
        target.HP = target.getHP() - bonusDamage;
        this.attack(target);
        
        this.SP = this.getSP() - 1;
    }
    
    /**
     * Method that lets the Monster take their turn
     * as long as they are not dead and it is their turn.
     * @param target. The target Hero
     */
    public void takeTurn(Creature target)
    {
        if(this.status.equals("Healthy") || this.status.equals("Poisoned"))
        {
            while(this.isMyTurn == true)
            {
                Random actionGenerator = new Random();
                int nextAction;
                
                if((this.getSP() > (int) this.maxSP/2) & (this.getHP() >= (int) this.maxHP/2))
                {
                    nextAction = actionGenerator.nextInt(3);
                    
                    if(nextAction == 0)
                    {
                        this.attack(target);
                    }
                    else if (nextAction == 1)
                    {
                        this.poisonSwipe(target);
                    }
                    else if (nextAction == 2)
                    {
                        this.furyAttack(target);
                    }
                    else if (nextAction == 3)
                    {
                        this.piercingBlow(target);
                    } 
                }
                
                else if((this.getSP() >= 2) & (this.getHP() < (int) this.maxHP/2))
                {
                    this.calmingCleanse(target);
                }
                
                else if(this.getSP() == 1)
                {
                    this.piercingBlow(target);
                }
                
                else
                {
                    nextAction = actionGenerator.nextInt(1);
                    
                    if(nextAction == 0)
                    {
                        this.attack(target);
                    }
                    else if(nextAction == 1)
                    {
                        this.defend(target);
                    }
                }
            }
        }
    }
}
