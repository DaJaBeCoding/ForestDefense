/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.network.server.serverGameplay.projectiles;

import maggdaforestdefense.network.CommandArgument;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.network.server.serverGameplay.Damage;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.HitBox;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.ServerGame;
import maggdaforestdefense.network.server.serverGameplay.Upgrade;
import maggdaforestdefense.network.server.serverGameplay.mobs.Mob;
import maggdaforestdefense.network.server.serverGameplay.towers.Maple;
import maggdaforestdefense.network.server.serverGameplay.towers.Tower;

/**
 *
 * @author DavidPrivat
 */
public class MapleShot extends Projectile {

    public final static double WIDTH = 10;
    public final static double DAMAGE = 40;
    private double EXPANSION = 500;


    private double xPos, yPos;
    private double currentRadius;
    private ServerGame serverGame;
    
    private Damage damageObject;
    private Damage.NormalDamage usualDamage;
    
    private HitBox.DonutHitBox donutHitBox;
    
    private double maxRadius;
    
    //upgrade
    private boolean isAusbau = false;
    private Damage.NormalMultiplier ausbauDamage;
    private final int mobsInRange;
    
    //upgrade constants
    public final static double MAX_AUSBAU_MULTIPLIER = 2;

    public MapleShot(int id, double xPos, double yPos, Tower owner, Tower.CanAttackSet attackSet, ServerGame serverGame, int mobsInRange, double range) {
        super(id, GameObjectType.P_MAPLE_SHOT, new HitBox.DonutHitBox(WIDTH, xPos, yPos), owner, attackSet);
        currentRadius = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.serverGame = serverGame;
        this.maxRadius = range * MapCell.CELL_SIZE;
        this.mobsInRange = mobsInRange;
        
        
        donutHitBox = (HitBox.DonutHitBox)super.hitBox;
        
        
        //Damage objects
        damageObject = new Damage(this);
        
        
        usualDamage = new Damage.NormalDamage(DAMAGE);
        ausbauDamage = new Damage.NormalMultiplier(1);
        
        damageObject.addAllDamage(new Damage.DamageSubclass[]{usualDamage});
        damageObject.addAllDamageMultiplier(new Damage.DamageMultiplier[]{ausbauDamage});
        
        
        owner.getUpgrades().forEach((Upgrade upgrade) -> {
            addUpgrade(upgrade);
        });
    }

    @Override
    public void dealDamage(Mob target) {
        if (!mobsDamaged.contains(target)) {
            mobsDamaged.add(target);
            
            if(isAusbau) {
                ausbauDamage.setMultiplier(4.0d / Math.pow((double)mobsInRange + 1.0d,2.0d) + 1.0d);
            }
            
            notifyOwnerDamage(target.damage(damageObject));
        }
    }

    @Override
    public CommandArgument[] toNetworkCommandArgs() {
        return new CommandArgument[]{new CommandArgument("x", String.valueOf(xPos)),
            new CommandArgument("y", String.valueOf(yPos)),
            new CommandArgument("type", String.valueOf(GameObjectType.P_MAPLE_SHOT.ordinal())),
            new CommandArgument("id", String.valueOf(id)),
            new CommandArgument("radius", String.valueOf(currentRadius))};
    }
    
    public void addUpgrade(Upgrade upgrade) {
        switch(upgrade) {
            case MAPLE_1_1:     //AUSBAU
                isAusbau = true;
                break;
        }
    }

    public boolean updateFlight(double timeElapsed) {
        currentRadius += timeElapsed * EXPANSION;
        donutHitBox.setInnerRadius(currentRadius);
        hitBox.updatePos(xPos, yPos);
        return currentRadius < maxRadius;
    }

    @Override
    public NetworkCommand update(double timeElapsed) {
        boolean stillExists = updateFlight(timeElapsed);

        collision(serverGame.getMobs());
        if (stillExists) {
            return new NetworkCommand(NetworkCommand.CommandType.UPDATE_GAME_OBJECT, new CommandArgument[]{new CommandArgument("x", String.valueOf(xPos)),
                new CommandArgument("y", String.valueOf(yPos)),
                new CommandArgument("id", String.valueOf(id)),
                new CommandArgument("radius", String.valueOf(currentRadius))});
        } else {
            serverGame.removeProjectile(this);
            return null;
        }
    }
}