package com.cadieux.baseballstats.mapping.StatsDisplayers;

import com.cadieux.baseballstats.dataLayer.Season;
import com.cadieux.baseballstats.presentationLayer.SeasonListerController;
import org.mapstruct.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeasonStatsResponseMapper {
    @Mappings({
            @Mapping(expression = "java(entity.getPlayer().getPlayerId())", target = "playerId"),

            @Mapping(expression = "java(entity.getBattingStats().getAb())", target = "ab"),
            @Mapping(expression = "java(entity.getBattingStats().getPa())", target = "pa"),
            @Mapping(expression = "java(entity.getBattingStats().getBb())", target = "bb"),
            @Mapping(expression = "java(entity.getBattingStats().getSingles())", target = "singles"),
            @Mapping(expression = "java(entity.getBattingStats().getDoubles())", target = "doubles"),
            @Mapping(expression = "java(entity.getBattingStats().getTriples())", target = "triples"),
            @Mapping(expression = "java(entity.getBattingStats().getHr())", target = "hr"),
            @Mapping(expression = "java(entity.getBattingStats().getHbp())", target = "hbp"),


            @Mapping(expression = "java(entity.getBattingStats().getSingles() + entity.getBattingStats().getDoubles() + entity.getBattingStats().getTriples() + entity.getBattingStats().getHr())", target = "b_h"),
            @Mapping(expression = "java(entity.getBattingStats().getAb() == 0 ? 0 : (double)(entity.getBattingStats().getSingles() + entity.getBattingStats().getDoubles() + entity.getBattingStats().getTriples() + entity.getBattingStats().getHr()) / entity.getBattingStats().getAb())", target = "avg"),
            @Mapping(expression = "java(entity.getBattingStats().getPa() == 0 ? 0 : (double)(entity.getBattingStats().getSingles() + entity.getBattingStats().getDoubles() + entity.getBattingStats().getTriples() + entity.getBattingStats().getHr() + entity.getBattingStats().getBb()) / entity.getBattingStats().getAb())", target = "obp"),


            @Mapping(expression = "java(entity.getPitchingStats().getIp())", target = "ip"),
            @Mapping(expression = "java(entity.getPitchingStats().getGs())", target = "gs"),
            @Mapping(expression = "java(entity.getPitchingStats().getSv())", target = "sv"),
            @Mapping(expression = "java(entity.getPitchingStats().getH())", target = "h"),
            @Mapping(expression = "java(entity.getPitchingStats().getEr())", target = "er"),
            @Mapping(expression = "java(entity.getPitchingStats().getK())", target = "k"),
            @Mapping(expression = "java(entity.getPitchingStats().getBb())", target = "p_bb"),
            @Mapping(expression = "java(entity.getPitchingStats().getW())", target = "w"),
            @Mapping(expression = "java(entity.getPitchingStats().getL())", target = "l"),

            @Mapping(expression = "java(entity.getPitchingStats().getIp() == 0 ? 0 : (double)(entity.getPitchingStats().getEr()) / entity.getPitchingStats().getIp() * 9)", target = "era"),
            @Mapping(expression = "java((entity.getPitchingStats().getW() + entity.getPitchingStats().getL()) == 0 ? 0 : (double)(entity.getPitchingStats().getW()) / (entity.getPitchingStats().getW() + entity.getPitchingStats().getL()))", target = "WPercentage")
    })
    SeasonStatsResponseModel entityToResponseModel(Season entity);

    List<SeasonStatsResponseModel> entityListToResponseModelList(List<Season> entities);

    @AfterMapping
    default void addLinks(@MappingTarget SeasonStatsResponseModel model, Season entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeasonListerController.class).getSeasonBySeasonId(entity.getSeasonId())).withSelfRel();
        //Link battingLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BattingStatsRepository.class).findBattingStatsByBattingId(entity.getBattingStats().getBattingId())).withRel("Batting Stats");
        //Link pitchingLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PitchingStatsRepository.class).findPitchingStatsByPitchingId(entity.getPitchingStats().getPitchingId())).withRel("Pitching Stats");
        model.add(selfLink);
        //model.add(battingLink);
        //model.add(pitchingLink);
    }
}
