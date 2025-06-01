package ch.supsi.minesweeper.data;

import ch.supsi.minesweeper.model.Constant;
import ch.supsi.minesweeper.model.TileModel;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileModelCsvExporter {
    private TileModelCsvExporter() {
    }

    public static void exportToFile(TileModel[][] tiles, File file) throws IOException{
        List<TileEntry> entries = new ArrayList<>();
        for (int y = 0; y < Constant.GRID_WIDTH; y++) {
            for (int x = 0; x < Constant.GRID_HEIGHT; x++) {
                entries.add(new TileEntry(x,y,tiles[x][y]));
            }
        }
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TileEntry.class).withHeader();
        try (FileWriter writer = new FileWriter(file)) {
            SequenceWriter sequenceWriter = mapper.writer(schema).writeValues(writer);
            sequenceWriter.writeAll(entries);
        }
    }
}
