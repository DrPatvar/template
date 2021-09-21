package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {
    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping (method = RequestMethod.GET, value = "/rest/players/{id}")
    public Player getPlayer(@PathVariable Long id){
        return playerService.getPlayer(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rest/players")
    public ResponseEntity <List<Player>> getAllPlayers(){
            final List<Player> allPlayer = playerService.getAllPlayer();

        return allPlayer!=null && !allPlayer.isEmpty()
                ? new ResponseEntity<>(allPlayer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rest/players")
    public void createPlayer(@RequestBody Player player){
        playerService.createPlayer(player);
    }



    @RequestMapping(method = RequestMethod.POST, value = "/rest/players/{id}")
    public boolean update(@RequestBody Player player, Long id){
        return playerService.update(player,id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rest/players/{id}")
    public boolean delete (@RequestBody Long id){
        return playerService.delete(id);
    }

    @RequestMapping(path = "/rest/players", method = RequestMethod.GET)
    public ResponseEntity<List<Player>> getFilteredPlayers(String name,
                                                           String title,
                                                           Race race,
                                                           Profession profession,
                                                           Long after,
                                                           Long before,
                                                           Boolean banned,
                                                           Integer minLevel,
                                                           Integer maxLevel,
                                                           PlayerOrder order,
                                                           Integer pageNumber,
                                                           Integer pageSize) {
        List<Player> result = null;
        List<Player> allFilteredPlayers = playerService.getFilterPlayer(name, title, race, profession, after, before, banned, minLevel, maxLevel);
        if (order != null){
            result =  playerService.sortPlayersList(allFilteredPlayers, order);
        } else result = allFilteredPlayers;
        List<Player> pageList = playerService.getPage(result, pageNumber, pageSize);

        return result != null && !result.isEmpty()
                ? new ResponseEntity<>(pageList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(path = "/rest/players/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCountPlayers(String name,
                                                   String title,
                                                   Race race,
                                                   Profession profession,
                                                   Long after,
                                                   Long before,
                                                   Boolean banned,
                                                   Integer minLevel,
                                                   Integer maxLevel,
                                                   PlayerOrder order,
                                                   Integer pageNumber,
                                                   Integer pageSize){
        List<Player> result = playerService.getFilterPlayer(name, title, race, profession, after, before, banned, minLevel, maxLevel);
        return result != null
                ? new ResponseEntity<>(playerService.getCountPlayers(result), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
