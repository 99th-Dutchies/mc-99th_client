package nl._99th_client.ccg;

public enum Gamemode {
    BLOCKWARS("blockwars_core_ctf", "BlockWars"),
    BLOCKWARS_CTF("blockwars_ctf", "BlockWars CTF"),
    BLOCKWARS_CORE("blockwars_core", "BlockWars CORE"),
    BLOCKWARS_BRIDGES("blockwars_bridges", "BlockWars Bridges"),
    SOLO_SKYWARS("solo_skywars", "Solo SkyWars"),
    TEAM_SKYWARS("team_skywars", "Team SkyWars"),
    SOLO_EGGWARS("solo_eggwars", "Solo EggWars"),
    TEAM_EGGWARS("team_eggwars", "Team EggWars"),
    SOLO_LUCKY_ISLANDS("solo_lucky_islands", "Solo Lucky Islands"),
    TEAM_LUCKY_ISLANDS("team_lucky_islands", "Team Lucky Islands"),
    DUELS("duels", "Duels"),
    FFA("ffa", "FFA"),
    ASSASSINATION("assassination", "Assassination"),
    MINERWARE("minerware", "MinerWare"),
    TOWER_DEFENCE("tower_defence", "Tower Defence"),
    SKYBLOCK("skyblock", "Skyblock"),
    SIMPLE_PARKOUR("simple_parkour", "Simple Parkour"),
    EASY_PARKOUR("easy_parkour", "Easy Parkour"),
    MEDIUM_PARKOUR("medium_parkour", "Medium Parkour"),
    HARD_PARKOUR("hard_parkour", "Hard Parkour"),
    PARKOUR("parkour", "Parkour"),
    COMPETITIVE_PARKOUR("competitive_parkour", "Competitive Parkour"),
    SLIME_SURVIVAL("slime_survival", "Slime Survival"),
    QUAKE_CRAFT("quake_craft", "Quake Craft"),
    PAINTBALL("paintball", "Paintball"),
    LAYER_SPLEEF("layer_spleef", "Layer Spleef"),
    WING_RUSH("wing_rush", "Wing Rush"),
    BATTLE_ZONE("battle_zone", "Battle Zone"),
    ARCHER_ASSAULT("archer_assault", "Archer Assault"),
    SOLO_SURVIVAL_GAMES("solo_survival_games", "Solo Survival Games"),
    TEAM_SURVIVAL_GAMES("team_survival_games", "Team Survival Games"),
    AMONG_SLIMES("among_slimes", "Among Slimes"),
    LINE_DASH("line_dash", "Line Dash"),
    UNKNOWN("unknown", "Unknown");

    private final String name;
    private final String displayName;

    Gamemode(String name, String displayName)
    {
        this.name = name;
        this.displayName = displayName;
    }

    public String toString()
    {
        return this.getString();
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getString() {
        return this.name;
    }
}
