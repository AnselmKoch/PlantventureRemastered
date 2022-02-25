package me.anselm.utils.font;

import me.anselm.graphics.Window;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.FileUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextFont {

    private File fontFile;
    private File fontImage; // 512 * 512;
    private Texture fontTexture;
    private Map<String, RenderChar[]> usedStrings;

    private float fontAspecRatio = 1.0f / 512f;

    private Map<Character, Glyph> glyphMap;

    public TextFont(String name) {
        String path = FileUtils.resourcePath + "fonts/" + name;
        fontFile = new File(path + ".fnt");
        fontImage = new File(path + ".png");
        fontTexture = new Texture("fonts/" + name);
        glyphMap = new HashMap<>();
        usedStrings = new HashMap<>();

        try {
            readFile(fontFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawTextCentered(String text, Vector3f position, float width, float height, Vector4f color) {
        RenderChar[] renderChars = new RenderChar[text.length()];

        float totalWidth = 0.0f;
        for(int i = 0; i < text.length(); i++) {

            if(text.charAt(i) == ' ') {
                continue;
            }

            Glyph glyph = glyphMap.get(text.charAt(i));
            float charWidth = glyph.width / width;
            totalWidth += charWidth;
        }
        float currX = position.x - (totalWidth / 2f), currY = position.y;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                continue;
            }


            Glyph glyph = glyphMap.get(c);

            float xTex = glyph.x * fontAspecRatio, yTex = glyph.y * fontAspecRatio;
            float yHeight = glyph.height * fontAspecRatio,xWidth = glyph.width * fontAspecRatio;
            Vector2f[] texCords = new Vector2f[]{
                    new Vector2f(xTex + xWidth, yTex),
                    new Vector2f(xTex + xWidth, yTex + yHeight),
                    new Vector2f(xTex, yTex + yHeight),
                    new Vector2f(xTex, yTex),

            };

            float charWidth = glyph.width / width;
            float charHeight = glyph.height / height;
            float charXOffset = glyph.xOffset / width;
            float charYOffset = glyph.yOffset / height;

            float widthTotal = 0f;
            RenderChar renderChar = new RenderChar(c,
                    new Vector3f(currX + charXOffset, currY- charYOffset - charHeight, 1.0f),
                    texCords, charWidth, charHeight + charYOffset, 1.0f, fontTexture, Position.BOTTOMLEFT, color);

            renderChars[i] = renderChar;

            currX += charWidth + charXOffset;
        }
        FontRenderer.addText(renderChars);
    }

    public void drawText(String text, Vector3f position, float width, float height, Vector4f color) {
        RenderChar[] renderChars = new RenderChar[text.length()];

        float currX = position.x, currY = position.y;
       for(int i = 0; i < text.length(); i++) {
           char c = text.charAt(i);
           if (c == ' ') {
               return;
           }


           Glyph glyph = glyphMap.get(c);

           float xTex = glyph.x * fontAspecRatio, yTex = glyph.y * fontAspecRatio;
           float yHeight = glyph.height * fontAspecRatio,xWidth = glyph.width * fontAspecRatio;
           Vector2f[] texCords = new Vector2f[]{
                   new Vector2f(xTex + xWidth, yTex),
                   new Vector2f(xTex + xWidth, yTex + yHeight),
                   new Vector2f(xTex, yTex + yHeight),
                   new Vector2f(xTex, yTex),

           };

           float charWidth = glyph.width / width;
           float charHeight = glyph.height / height;
           float charXOffset = glyph.xOffset / width;
           float charYOffset = glyph.yOffset / height;


           RenderChar renderChar = new RenderChar(c,
                   new Vector3f(currX, currY- charYOffset - charHeight, 1.0f),
                   texCords, charWidth + charXOffset, charHeight + charYOffset, 1.0f, fontTexture, Position.BOTTOMLEFT, color);

           renderChars[i] = renderChar;

           currX += charWidth + charXOffset;
       }
        FontRenderer.addText(renderChars);
    }

    /**
     * Reads every info for every char inside the font textfile and creates a Glyph for every char with its information
     * @param file
     * @throws IOException
     */
    private void readFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        if(fontFile.exists() && fontImage.exists()) {
            String lastLine = "";
            while (lastLine != null) {
                if(lastLine.startsWith("char ")) {
                    String[] output = lastLine.split(" ");
                    ArrayList<String> strings = new ArrayList<>();

                    for (String string : output) {
                        if (!string.trim().isEmpty() && string.contains("=")) {
                            strings.add(string);
                        }
                    }
                    float[] values = new float[8];
                    for (int i = 0; i < strings.size(); i++) {
                        //char
                        if(i == 0) {
                            values[i] = Integer.valueOf(strings.get(0).replace("id=", ""));
                        }
                        //X
                        if(i == 1) {
                            values[i] = Integer.valueOf(strings.get(1).replace("x=", ""));
                        }
                        //Y
                        if(i == 2) {
                            values[i] = Integer.valueOf(strings.get(2).replace("y=",""));
                        }
                        //width
                        if(i == 3) {
                            values[i] = Integer.valueOf(strings.get(3).replace("width=",""));
                        }
                        //height
                        if(i == 4) {
                            values[i] = Integer.valueOf(strings.get(4).replace("height=",""));
                        }
                        //xoffset
                        if(i == 5) {
                            values[i] = Integer.valueOf(strings.get(5).replace("xoffset=",""));
                        }
                        //yoffset
                        if(i == 6) {
                            values[i] = Integer.valueOf(strings.get(6).replace("yoffset=",""));
                        }
                        //xadvance
                        if(i == 7) {
                            values[i] = Integer.valueOf(strings.get(7).replace("xadvance=",""));
                        }
                    }


                    Glyph glyph = new Glyph(values[1], values[2],values[3],values[4],values[5], values[6]);
                    char ascii = (char)values[0];
                    glyphMap.put(ascii,glyph);
                }
                lastLine = bufferedReader.readLine();
            }
        }
    }

    public File getFontFile() {
        return fontFile;
    }

    public File getFontImage() {
        return fontImage;
    }

    public Texture getFontTexture() {
        return this.fontTexture;
    }
    public Map<Character, Glyph> getGlyphMap() {
        return glyphMap;
    }

}