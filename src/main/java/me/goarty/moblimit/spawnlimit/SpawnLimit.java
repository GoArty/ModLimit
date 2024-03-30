package me.goarty.moblimit.spawnlimit;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpawnLimit implements Listener {
    private ArrayList<EntityType> listOfExclusion = new ArrayList<>(); // список энтити, еоторые входят в исключение
    private ArrayList<Material> listOfFood = new ArrayList<>(); // список энтити, еоторые входят в исключение

    public SpawnLimit(){
        defineListOfExclusion();

        defineListOfFood();
    }

    @EventHandler
    public void mobLimitSpawnInChunk(CreatureSpawnEvent e){
        Chunk ch = e.getEntity().getLocation().getChunk(); //получение чанка, где происходит спаун
        int k = countProhibitedEntity(ch); // счётчик энтити попадающих под ограничение

        if(!e.isCancelled() && k > 64)// если ивент не был отменён и счётчик > 64 - отменить появление
            e.setCancelled(true);

    }

    @EventHandler
    public void loveModeOff(EntityEnterLoveModeEvent e){
        Chunk ch = e.getEntity().getLocation().getChunk(); //получение чанка, где происходит спаун
        int k = countProhibitedEntity(ch); // счётчик энтити попадающих под ограничение

        if(!e.isCancelled() && k > 64){// если ивент не был отменён и счётчик > 64 - отменить появление
            e.getHumanEntity().sendMessage("В чанке слишком много мобов, размножение недоступно");
            e.setCancelled(true);

            for(Material mat : listOfFood) // ищет в списке еду подходящую данному животному и отдаёт её в инвентарь игрока
                if(e.getEntity().isBreedItem(mat)){
                    ItemStack inv = new ItemStack(mat);
                    e.getHumanEntity().getInventory().addItem(inv); // проблемы с тем что может не быть места в ивенторе при добавления предмета вроде нет
                    break;
                }

        }
    }

    private int countProhibitedEntity(Chunk ch){
        int k = 0;
        for(Entity ent : ch.getEntities())
            if(!listOfExclusion.contains(ent))
                k += 1;
        return k;
    }

    private void defineListOfExclusion(){
        listOfExclusion.add(EntityType.PLAYER);
        listOfExclusion.add(EntityType.DROPPED_ITEM);
        listOfExclusion.add(EntityType.GLOW_ITEM_FRAME);
        listOfExclusion.add(EntityType.ITEM_DISPLAY);
        listOfExclusion.add(EntityType.ITEM_FRAME);
        listOfExclusion.add(EntityType.ARROW);
        listOfExclusion.add(EntityType.ALLAY);
        listOfExclusion.add(EntityType.AREA_EFFECT_CLOUD);
        listOfExclusion.add(EntityType.SMALL_FIREBALL);
        listOfExclusion.add(EntityType.ENDER_PEARL);
        listOfExclusion.add(EntityType.ENDER_SIGNAL);
        listOfExclusion.add(EntityType.FALLING_BLOCK);
        listOfExclusion.add(EntityType.FIREBALL);
        listOfExclusion.add(EntityType.FIREWORK);
        listOfExclusion.add(EntityType.FISHING_HOOK);
        listOfExclusion.add(EntityType.EXPERIENCE_ORB);
        listOfExclusion.add(EntityType.ARMOR_STAND);
    }

    private void defineListOfFood(){
        listOfFood.add(Material.APPLE);
        listOfFood.add(Material.CARROT);
        listOfFood.add(Material.WHEAT);
        listOfFood.add(Material.COD);
    }
}
