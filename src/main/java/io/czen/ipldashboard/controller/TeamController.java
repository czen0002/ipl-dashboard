package io.czen.ipldashboard.controller;

import io.czen.ipldashboard.model.Match;
import io.czen.ipldashboard.model.Team;
import io.czen.ipldashboard.repository.MatchRepository;
import io.czen.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping(value = "/team/{teamName}", produces = "application/json")
    public Team getTeam(@PathVariable String teamName) {

        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));

        return team;
    }

    @GetMapping(value = "/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1, 1, 1);
        List<Match> matches = this.matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
        return matches;
    }

}
