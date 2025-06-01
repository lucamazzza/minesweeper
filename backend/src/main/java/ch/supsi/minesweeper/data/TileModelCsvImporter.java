package ch.supsi.minesweeper.data;

import ch.supsi.minesweeper.model.Constant;
import ch.supsi.minesweeper.model.TileModel;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class TileModelCsvImporter {
    private TileModelCsvImporter() {
    }

    public static TileModel[][] importTilesFromSave(File file) throws IOException {
        TileModel[][] tiles = new TileModel[Constant.GRID_HEIGHT][Constant.GRID_WIDTH];
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<TileEntry> iterator = mapper.readerFor(TileEntry.class).with(schema).readValues(file);
        int tileCount = 0;
        while (iterator.hasNext() && tileCount < Constant.TILE_COUNT) {
            TileEntry tileEntry = iterator.next();
            tiles[tileEntry.getRow()][tileEntry.getCol()] = tileEntry.getTile();
            tileCount++;
        }

        return tiles;
    }
}
