package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayer();
    List<Player> getFilterPlayer(String name,
                                 String title,
                                 Race race,
                                 Profession profession,
                                 Long after,
                                 Long before,
                                 Boolean banned,
                                 Integer minLevel,
                                 Integer maxLevel

    );
    List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize);
    List<Player> sortPlayersList(List<Player> players, PlayerOrder playerOrder);
    void createPlayer(Player player);
    boolean update (Player player, Long id);
    boolean delete (Long id);
    Player getPlayer(Long id);
     Integer getCountPlayers(List<Player> playerList);

}
