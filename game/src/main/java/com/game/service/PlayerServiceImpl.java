package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
        private  PlayerRepository playerRepository;


    /* public PlayerServiceImpl( PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
     }*/

    @Override
    public List<Player> getAllPlayer() {
        return playerRepository.findAll();
    }
    @Override
    public List<Player> getFilterPlayer(String name,
                                        String title,
                                        Race race,
                                        Profession profession,
                                        Long after,
                                        Long before,
                                        Boolean banned,
                                        Integer minLevel,
                                        Integer maxLevel){
        final Date afterDate = after == null ? null : new Date(after);
        final Date beforeDate = before == null ? null : new Date(before);
        final List<Player> allFilterPlayer = new ArrayList<>();
        playerRepository.findAll().forEach(player -> {
            if (name != null && !player.getName().contains(name)) return;
            if (title != null && !player.getTitle().contains(title)) return;
            if (race !=null && player.getRace() != race ) return;
            if (profession !=null && player.getProfession() != profession)return;
            if (after !=null && player.getBirthday().before(afterDate))return;
            if (before !=null && player.getBirthday().after(beforeDate))return;
            if (banned !=null && player.getBanned() !=banned) return;
            if (minLevel !=null && player.getLevel() < minLevel) return;
            if (maxLevel !=null && player.getLevel() > maxLevel) return;

            allFilterPlayer.add(player);
        });
        return allFilterPlayer;
    }
    @Override
    public List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize) {
        final Integer page = pageNumber == null ? 0 : pageNumber;
        final Integer size = pageSize == null ? 3 : pageSize;
        final int from = page * size;
        int to = from + size;
        if (to > players.size()) to = players.size();
        return players.subList(from, to);
    }
    @Override
    public List<Player> sortPlayersList(List<Player> players, PlayerOrder order) {
        if (order != null) {
            players.sort((ship1, ship2) -> {
                switch (order) {
                    case ID: return ship1.getId().compareTo(ship2.getId());
                    case NAME: return ship1.getName().compareTo(ship2.getName());
                    case EXPERIENCE: return ship1.getExperience().compareTo(ship2.getExperience());
                    case BIRTHDAY: return ship1.getBirthday().compareTo(ship2.getBirthday());
                    default: return 0;
                }
            });
        }
        return players;
    }

    @Override
    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public Player getPlayer(Long id) {
        return playerRepository.getOne(id);
    }

    @Override
    public boolean delete(Long id) {
        if (playerRepository.existsById(id)){
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Player player, Long id) {
        if (playerRepository.existsById(id)){
            player.setId(id);
            playerRepository.save(player);
            return true;
        }
        return false;
    }

    @Override
    public Integer getCountPlayers(List<Player> playerList) {
        return playerList.size();
    }
}
