package ch.supsi.minesweeper.data;

import ch.supsi.minesweeper.model.TileModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class TileEntry {
    @JsonProperty("row")
    private int row;
    @JsonProperty("col")
    private int col;
    @JsonProperty("isBomb")
    private boolean bomb;
    @JsonProperty("isMarked")
    private boolean marked;
    @JsonProperty("isExploded")
    private boolean exploded;
    @JsonProperty("isUncovered")
    private boolean uncovered;
    @JsonProperty("adjBombs")
    private int adjBombs;

    @JsonIgnore
    public TileEntry(int row, int col, TileModel tile) {
        this.row = row;
        this.col = col;
        this.bomb = tile.isBomb();
        this.marked = tile.isMarked();
        this.exploded = tile.isExploded();
        this.uncovered = tile.isUncovered();
        this.adjBombs = tile.getAdjBombs();
    }

    @JsonIgnore
    public TileModel getTile() {
        TileModel tile = new TileModel();
        tile.setBomb(bomb);
        tile.setMarked(marked);
        tile.setExploded(exploded);
        tile.setUncovered(uncovered);
        tile.setAdjBombs(adjBombs);
        return tile;
    }
}
