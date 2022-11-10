package com;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PhanTichTuVung {
    private final static int ERROR_STATE = 1000;
    private static String currentToken ="";
    private static String allTokens = "";
    private static String allAttributes = "";
    private static List<TokenAttribute> listTkAtt = new ArrayList<>();
    private static Map<Integer, Integer> startBrandIndexList = new HashMap<Integer,Integer>(){{
        put(0,50);
        put(50,100);
        put(100,300);
        put(300,ERROR_STATE);
    }};
    private static int endWithoutStarStates[] = new int[]{1,2,3,4,5,6,7,8,9,10,11,14,20,22,
                                                        28,29,52,53,56,57,60,63,66,69,70,73,74,
                                                        77,80,84,86,90,92,94,95}; //35
    private static int endWithStarStates[] = new int []{13,21,25,30,54,58,61,64,67,71,75,
                                                        78,81,85,87,91,93,103,105,109,112,
                                                        113,115,119,120,124,125,127,128,130,132,201,500}; //33
    public PhanTichTuVung(){

    }
    public static void main(String[] args) throws IOException {
        Path path = Path.of("D:\\TaiLieu\\ChuongTrinhDich\\PhanTichTuVung\\src\\com\\input.txt");
        String input = Files.readString(path);
        while (input.indexOf(13)>0)
            input = input.substring(0,input.indexOf(13))+input.substring(input.indexOf(13)+1);
        System.out.println(input);
        GetTokens(input+"\0");
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String input = scanner.nextLine();
//            input += '\0';
//            System.out.println(input.substring(0, input.indexOf('\0')));
//            GetTokens(input);
//        }
    }

    public static void GetTokens(String input){
        allTokens = "";
        allAttributes = "";
        int state = 0;
        int errorPosition = -1;
        for (int i = 0;i<input.length();i++){
            char currentChar = input.charAt(i);
//            System.out.println("state: "+state+" - next char is: "+ currentChar);
            switch(state) {
                //nhanh toan tu
                //
                //
                case 0:
                    switch (currentChar) {
                        case '\n':
                            allTokens +="\n";
                            allAttributes +="\n";
                            i++;
                            break;
                        case ' ':
                            i++;
                            break;
                        case '{':
                            state = 1;
                            break;
                        case '}':
                            state = 2;
                            break;
                        case '[':
                            state = 3;
                            break;
                        case ']':
                            state = 4;
                            break;
                        case '(':
                            state = 5;
                            break;
                        case ')':
                            state = 6;
                            break;
                        case '#':
                            state = 7;
                            break;
                        case '?':
                            state = 8;
                            break;
                        case ':':
                            state = 9;
                            break;
                        case ',':
                            state = 10;
                            break;
                        case ';':
                            state = 11;
                            break;
                        case '"':
                            state = 12;
                            break;
                        case '\'':
                            state = 16;
                            break;
                        case '/':
                            state = 23;
                            break;
                        case '_':
                            state = 200;
                            break;
                        default:
                            state = startBrandIndexList.get(state);
                            break;
                    }
                    break;
                case 12:
                    if (currentChar == '\n' || currentChar == '\0') {
                        errorPosition = i;
                        state = 13;
                    }
                    else if (currentChar == '"') state = 14;
                    else if (currentChar == '\\') state = 15;
                    break;
                case 15:
                    if (currentChar == '\0') state = 13;
                    else state = 12;
                    break;
                case 16:
                    switch (currentChar) {
                        case '\n':
                        case '\0':
                            state = 21;
                            errorPosition = i;
                            break;
                        case '\\':
                            state = 17;
                            break;
                        case '\'':
                            errorPosition = i;
                            state = 22;
                            break;
                        default:
                            state = 18;
                            break;
                    }
                    break;
                case 17:
                case 19:
                    if (currentChar == '\0') state = 21;
                    else state = 18;
                    break;
                case 18:
                    if (currentChar == '\\') state = 19;
                    else if (currentChar == '\'') state = 20;
                    else if (currentChar == '\n') state = 21;
                    break;
                case 23:
                    switch (currentChar) {
                        case '/':
                            state = 24;
                            break;
                        case '*':
                            state = 26;
                            break;
                        case '=':
                            state = 29;
                            break;
                        default:
                            state = 30;
                            break;
                    }
                    break;
                case 24:
                    if (currentChar == '\n') state = 25;
                    break;
                case 26:
                    if (currentChar == '*') state = 27;
                    break;
                case 27:
                    if (currentChar == '/') state = 28;
                    else if (currentChar != '*') state = 26;
                    break;
                case 200:
                    if (!(Character.isLetterOrDigit(currentChar) || currentChar == '_'))
                        state = 201;
                    break;
                //nhanh toan tu 2
                //
                //
                case 50:
                    switch (currentChar) {
                        case '+':
                            state = 51;
                            break;
                        case '-':
                            state = 55;
                            break;
                        case '*':
                            state = 59;
                            break;
                        case '%':
                            state = 62;
                            break;
                        case '^':
                            state = 65;
                            break;
                        case '&':
                            state = 68;
                            break;
                        case '|':
                            state = 72;
                            break;
                        case '=':
                            state = 76;
                            break;
                        case '!':
                            state = 79;
                            break;
                        case '>':
                            state = 82;
                            break;
                        case '<':
                            state = 88;
                            break;
                        case '~':
                            state = 94;
                            break;
                        case '@':
                        case '$':
                        case '`':
                            errorPosition = i;
                            state = 95;
                            break;
                        default:
                            state = startBrandIndexList.get(state);
                            break;
                    }
                    break;
                case 51:
                    if (currentChar == '+') state = 52;
                    else if (currentChar == '=') state = 53;
                    else state = 54;
                    break;
                case 55:
                    if (currentChar == '-') state = 56;
                    else if (currentChar == '=') state = 57;
                    else state = 58;
                    break;
                case 59:
                    if (currentChar == '=') state = 60;
                    else state = 61;
                    break;
                case 62:
                    if (currentChar == '=') state = 63;
                    else state = 64;
                    break;
                case 65:
                    if (currentChar == '=') state = 66;
                    else state = 67;
                    break;
                case 68:
                    if (currentChar == '=') state = 69;
                    else if (currentChar == '&') state = 70;
                    else state = 71;
                    break;
                case 72:
                    if (currentChar == '=') state = 73;
                    else if (currentChar == '|') state = 74;
                    else state = 75;
                    break;
                case 76:
                    if (currentChar == '=') state = 77;
                    else state = 78;
                    break;
                case 79:
                    if (currentChar == '=') state = 80;
                    else state = 81;
                    break;
                case 82:
                    if (currentChar == '>') state = 83;
                    else if (currentChar == '=') state = 86;
                    else state = 87;
                    break;
                case 83:
                    if (currentChar == '=') state = 84;
                    else state = 85;
                    break;
                case 88:
                    if (currentChar == '<') state = 89;
                    else if (currentChar == '=') state = 92;
                    else state = 93;
                    break;
                case 89:
                    if (currentChar == '=') state = 90;
                    else state = 91;
                    break;
                //nhanh cac kieu so
                //
                //
                case 100:
                    switch (currentChar) {
                        case '0':
                            state = 101;
                            break;
                        case '.':
                            state = 126;
                            break;
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            state = 114;
                            break;
                        default: state = startBrandIndexList.get(state);
                    }
                    break;
                case 101:
                    if (currentChar=='x'||currentChar=='X') state = 116;
                    else if (currentChar=='b'||currentChar=='B') state = 121;
                    else if (currentChar=='.') state = 110;
                    else if (currentChar=='8'||currentChar=='9') state = 106;
                    else if (Character.isLetter(currentChar)) {
                        errorPosition = i;
                        state = 104;
                    }
                    else if (currentChar <= '7' && currentChar >= '0') state = 102;
                    else state = 128;
                    break;
                case 102:
                    if (currentChar == '.') state = 110;
                    else if (Character.isLetter(currentChar)) {
                        errorPosition = i;
                        state = 104;
                    }
                    else if (currentChar == '8' || currentChar == '9') {
                        errorPosition = i;
                        state = 106;
                    }
                    else if (currentChar > '7' || currentChar < '0') state = 103;
                    break;
                case 104:
                    if (!Character.isLetterOrDigit(currentChar)&&currentChar!='.') state = 105;
                    break;
                case 106:
                    if (currentChar=='.') state = 110;
                    else if (Character.isLetter(currentChar))   state = 108;
                    else state = 109;
                    break;
                case 108:
                    if (!Character.isLetterOrDigit(currentChar)) state = 109;
                    break;
                case 110:
                    if (currentChar=='e'||currentChar=='E') state = 129;
                    else if (currentChar=='f'||currentChar=='F') state = 131;
                    else if (Character.isLetter(currentChar)||currentChar=='.') {
                        errorPosition = i;
                        state = 111;
                    }
                    else if(currentChar > '9' || currentChar < '0') state = 113;
                    break;
                case 111:
                    if (!Character.isLetterOrDigit(currentChar)&&currentChar!='.') state = 112;
                    break;
                case 114:
                    if (currentChar=='e') state = 129;
                    else if (Character.isLetter(currentChar)) {
                        errorPosition = i;
                        state = 104;
                    }
                    else if (currentChar=='.') state = 110;
                    else if (currentChar > '9' || currentChar < '0') state = 115;
                    break;
                case 116:
                    if ((currentChar<='9'&&currentChar>='0')||
                            (currentChar<='f'&&currentChar>='a')||
                            (currentChar<='F'&&currentChar>='A'))
                        state = 117;
                    else if (Character.isLetter(currentChar)) {
                        errorPosition = i;
                        state = 104;
                    }
                    else {
                        errorPosition = i;
                        state = 105;
                    }
                    break;
                case 117:
                    if ((currentChar<='z'&&currentChar>='g')||
                            (currentChar<='Z'&&currentChar>='G')) {
                        errorPosition = i;
                        state = 118;
                    }
                    else if (!(Character.isLetterOrDigit(currentChar)))
                        state = 120;
                    break;
                case 118:
                    if (!Character.isLetterOrDigit(currentChar)) state = 119;
                    break;
                case 121:
                    if (currentChar=='0'||currentChar=='1') state = 122;
                    else if(Character.isLetterOrDigit(currentChar)) {
                        errorPosition = i;
                        state =104;
                    }
                    else {
                        errorPosition = i;
                        state = 105;
                    }
                    break;
                case 122:
                    if (currentChar!='0' && currentChar!='1'){
                        if (Character.isLetterOrDigit(currentChar)) {
                            errorPosition = i;
                            state = 123;
                        }
                        else state = 125;
                    }
                    break;
                case 123:
                    if(!Character.isLetterOrDigit(currentChar)) state = 124;
                    break;
                case 126:
                    if(Character.isDigit(currentChar)) state = 110;
                    else state = 127;
                    break;
                case 129:
                    if (Character.isLetter(currentChar)||currentChar=='.') {
                        errorPosition = i;
                        state = 111;
                    }
                    else if (!Character.isDigit(currentChar)) state = 130;
                    break;
                case 131:
                    if (Character.isLetterOrDigit(currentChar)||currentChar=='.'){
                        errorPosition = i;
                        state = 111;
                    }
                    else state = 132;
                    break;
                //nhanh keyword, identified
                //
                //
                case 300:
                    if (currentChar == 'a') state = 301;
                    else if (currentChar == 'b') state = 305;
                    else if (currentChar == 'c') state = 310;
                    else if (currentChar == 'd') state = 326;
                    else if (currentChar == 'e') state = 338;
                    else if (currentChar == 'f') state = 350;
                    else if (currentChar == 'g') state = 357;
                    else if (currentChar == 'i') state = 361;
                    else if (currentChar == 'l') state = 365;
                    else if (currentChar == 'r') state = 369;
                    else if (currentChar == 's') state = 381;
                    else if (currentChar == 't') state = 409;
                    else if (currentChar == 'u') state = 416;
                    else if (currentChar == 'v') state = 427;
                    else if (currentChar == 'w') state = 437;
                    else if (Character.isLetter(currentChar)) state = 200;
                    else state = startBrandIndexList.get(state);
                    break;
                    //ky tu a
                //
                //
                case 301:
                    if (currentChar=='u') state = 302;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 302:
                    if (currentChar=='t') state = 303;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 303:
                    if (currentChar=='o') state = 304;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu b
                case 305:
                    if (currentChar=='r') state = 306;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 306:
                    if (currentChar=='e') state = 307;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 307:
                    if (currentChar=='a') state = 308;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 308:
                    if (currentChar=='k') state = 309;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu c
                //
                //
                case 310:
                    if (currentChar=='a') state = 311;
                    else if (currentChar=='h') state = 314;
                    else if (currentChar=='o') state = 317;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 311:
                    if (currentChar=='s') state = 312;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 312:
                    if (currentChar=='e') state = 313;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 314:
                    if (currentChar=='a') state = 315;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 315:
                    if (currentChar=='r') state = 316;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 317:
                    if (currentChar=='n') state = 318;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 318:
                    if (currentChar=='s') state = 319;
                    else if(currentChar=='t') state = 321;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 319:
                    if (currentChar=='t') state = 320;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 321:
                    if (currentChar=='i') state = 322;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 322:
                    if (currentChar=='n') state = 323;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 323:
                    if (currentChar=='u') state = 324;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 324:
                    if (currentChar=='e') state = 325;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky ty d
                //
                //
                case 326:
                    if (currentChar=='o') state = 327;
                    else if(currentChar=='e') state = 332;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 327:
                    if (currentChar=='u') state = 328;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 500;
                    break;
                case 328:
                    if (currentChar=='b') state = 329;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 329:
                    if (currentChar=='l') state = 330;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 330:
                    if (currentChar=='e') state = 331;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 332:
                    if (currentChar=='f') state = 333;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 333:
                    if (currentChar=='a') state = 334;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 334:
                    if (currentChar=='u') state = 335;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 335:
                    if (currentChar=='l') state = 336;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 336:
                    if (currentChar=='t') state = 337;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu e
                //
                //
                case 338:
                    if (currentChar=='l') state = 339;
                    else if(currentChar=='n') state = 342;
                    else if (currentChar=='x') state = 345;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 339:
                    if (currentChar=='s') state = 340;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 340:
                    if (currentChar=='e') state = 341;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 342:
                    if (currentChar=='u') state = 343;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 343:
                    if (currentChar=='m') state = 344;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 345:
                    if (currentChar=='t') state = 346;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 346:
                    if (currentChar=='e') state = 347;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 347:
                    if (currentChar=='r') state = 348;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 348:
                    if (currentChar=='n') state = 349;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu f
                //
                //
                case 350:
                    if (currentChar=='o') state = 351;
                    else if (currentChar=='l') state = 353;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 351:
                    if (currentChar=='e') state = 325;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 353:
                    if (currentChar=='o') state = 354;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 354:
                    if (currentChar=='a') state = 355;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 355:
                    if (currentChar=='t') state = 356;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu g
                //
                //
                case 357:
                    if (currentChar=='o') state = 358;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 358:
                    if (currentChar=='t') state = 359;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 359:
                    if (currentChar=='o') state = 360;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu i
                //
                //
                case 361:
                    if (currentChar=='f') state = 362;
                    else if (currentChar=='n') state = 363;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 363:
                    if (currentChar=='t') state = 364;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu l
                //
                //
                case 365:
                    if (currentChar=='o') state = 366;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 366:
                    if (currentChar=='n') state = 367;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 367:
                    if (currentChar=='g') state = 368;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu r
                //
                //
                case 369:
                    if (currentChar=='e') state = 370;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 370:
                    if (currentChar=='t') state = 371;
                    else if(currentChar=='g') state = 375;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 371:
                    if (currentChar=='u') state = 372;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 372:
                    if (currentChar=='r') state = 373;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 373:
                    if (currentChar=='n') state = 374;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 375:
                    if (currentChar=='i') state = 376;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 376:
                    if (currentChar=='s') state = 377;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 377:
                    if (currentChar=='t') state = 378;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 378:
                    if (currentChar=='e') state = 379;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 379:
                    if (currentChar=='r') state = 380;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu s
                //
                //
                case 381:
                    if (currentChar=='h') state = 382;
                    else if(currentChar=='w') state = 386;
                    else if(currentChar=='y') state = 391;
                    else if(currentChar=='i') state = 400;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 382:
                    if (currentChar=='o') state = 383;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 383:
                    if (currentChar=='r') state = 384;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 384:
                    if (currentChar=='t') state = 385;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 386:
                    if (currentChar=='i') state = 387;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 387:
                    if (currentChar=='t') state = 388;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 388:
                    if (currentChar=='c') state = 389;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 389:
                    if (currentChar=='h') state = 390;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 391:
                    if (currentChar=='r') state = 392;
                    else if(currentChar=='a') state = 396;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 392:
                    if (currentChar=='u') state = 393;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 393:
                    if (currentChar=='c') state = 394;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 394:
                    if (currentChar=='t') state = 395;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 396:
                    if (currentChar=='t') state = 397;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 397:
                    if (currentChar=='i') state = 398;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 398:
                    if (currentChar=='c') state = 399;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 400:
                    if (currentChar=='g') state = 401;
                    else if(currentChar=='z') state = 405;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 401:
                    if (currentChar=='n') state = 402;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 402:
                    if (currentChar=='e') state = 403;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 403:
                    if (currentChar=='d') state = 404;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 405:
                    if (currentChar=='e') state = 406;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 406:
                    if (currentChar=='o') state = 407;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 407:
                    if (currentChar=='f') state = 408;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu t
                //
                //
                case 409:
                    if (currentChar=='y') state = 410;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 410:
                    if (currentChar=='p') state = 411;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 411:
                    if (currentChar=='e') state = 412;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 412:
                    if (currentChar=='d') state = 413;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 413:
                    if (currentChar=='e') state = 414;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 414:
                    if (currentChar=='f') state = 415;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    // ky tu u
                //
                //
                case 416:
                    if (currentChar=='n') state = 417;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 417:
                    if (currentChar=='i') state = 418;
                    else if(currentChar=='s') state = 421;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 418:
                    if (currentChar=='o') state = 419;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 419:
                    if (currentChar=='n') state = 420;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 421:
                    if (currentChar=='i') state = 422;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 422:
                    if (currentChar=='g') state = 423;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 423:
                    if (currentChar=='n') state = 424;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 424:
                    if (currentChar=='e') state = 425;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 425:
                    if (currentChar=='d') state = 426;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu v
                //
                //
                case 427:
                    if (currentChar=='o') state = 428;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 428:
                    if (currentChar=='i') state = 429;
                    else if (currentChar=='l') state = 431;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 429:
                    if (currentChar=='d') state = 430;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 431:
                    if (currentChar=='a') state = 432;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 432:
                    if (currentChar=='t') state = 433;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 433:
                    if (currentChar=='i') state = 434;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 434:
                    if (currentChar=='l') state = 435;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 435:
                    if (currentChar=='e') state = 436;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //ky tu w
                //
                //
                case 437:
                    if (currentChar=='h') state = 438;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 438:
                    if (currentChar=='i') state = 439;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 439:
                    if (currentChar=='l') state = 440;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                case 440:
                    if (currentChar=='e') state = 441;
                    else if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 201;
                    break;
                    //cac trang thai ngat tu khoa
                case 304:
                case 309:
                case 313:
                case 316:
                case 320:
                case 325:
                case 331:
                case 337:
                case 341:
                case 344:
                case 349:
                case 352:
                case 356:
                case 360:
                case 362:
                case 364:
                case 368:
                case 374:
                case 380:
                case 385:
                case 390:
                case 395:
                case 399:
                case 404:
                case 408:
                case 415:
                case 420:
                case 426:
                case 430:
                case 436:
                case 441:
                    if (Character.isLetterOrDigit(currentChar)) state = 200;
                    else state = 500;
                    break;
            }
            if (state == ERROR_STATE){
                currentToken = "";
                break;
            }
            else if (startBrandIndexList.containsKey(state)){
                i--;
            }
            else if (isEndWithoutStar(state)){
                currentToken += currentChar;
                allTokens +=" "+currentToken;
                String currentAttribute = getAttribute(state);
                if (currentAttribute.startsWith("error")){
                    currentAttribute = "at-position-"+errorPosition+"-"+currentAttribute;
                }
                else if (currentAttribute.equals("keyword")) currentAttribute = currentToken;
                allAttributes += " "+currentAttribute;
                listTkAtt.add(new TokenAttribute(currentToken,currentAttribute));
                state = 0;
                currentToken = "";
            }
            else if (isEndWithStar(state)){
                allTokens +=" "+currentToken;
                String currentAttribute = getAttribute(state);
                if (currentAttribute.startsWith("error")){
                    currentAttribute = "at-position-"+errorPosition+"-"+currentAttribute;
                }
                else if (currentAttribute.equals("keyword")) currentAttribute = currentToken;
                allAttributes += " "+currentAttribute;
                listTkAtt.add(new TokenAttribute(currentToken,currentAttribute));
                i--;
                state = 0;
                currentToken = "";
            }
            else {
                currentToken += currentChar;
            }
        }
        System.out.println(allAttributes +"\n"+allTokens);
    }
    public static boolean isEndWithoutStar(int state){
        for (int i = 0;i< endWithoutStarStates.length;i++) {
            if (endWithoutStarStates[i] == state)
                return true;
        }
        return false;
    }
    public static  boolean isEndWithStar(int state){
        for (int i = 0;i< endWithStarStates.length;i++) {
            if (endWithStarStates[i] == state)
                return true;
        }
        return false;
    }
    public static String getAttribute(int endState){
        String attribute = "";
        switch (endState){
            case 1: attribute = "left-bracket"; break;
            case 2: attribute = "right-bracket"; break;
            case 3: attribute = "left-sbracket"; break;
            case 4: attribute = "right-sbracket"; break;
            case 5: attribute = "left-parenthesis"; break;
            case 6: attribute = "right-parenthesis"; break;
            case 7: attribute = "pound"; break;
            case 8: attribute = "question"; break;
            case 9: attribute = "colon"; break;
            case 10: attribute = "comma"; break;
            case 11: attribute = "semicolon"; break;
            case 13: attribute = "error-missing-\"-terminate"; break;
            case 14: attribute = "string-constant"; break;
            case 20: attribute = "char-constant"; break;
            case 21: attribute = "error-missing-\'-terminate"; break;
            case 22: attribute = "error-empty-char"; break;
            case 25: attribute = "one-line-cmt"; break;
            case 28: attribute = "multi-line-cmt"; break;
            case 29: attribute = "divide-assign"; break;
            case 30: attribute = "divide"; break;
            case 52: attribute = "plus-plus"; break;
            case 53: attribute = "plus-assign"; break;
            case 54: attribute = "plus"; break;
            case 56: attribute = "minus-minus"; break;
            case 57: attribute = "minus-assign"; break;
            case 58: attribute = "minus"; break;
            case 60: attribute = "multiply-assign"; break;
            case 61: attribute = "multiply"; break;
            case 63: attribute = "modulus-assign"; break;
            case 64: attribute = "modulus"; break;
            case 66: attribute = "xor-bitwise-assign"; break;
            case 67: attribute = "xor-bitwise"; break;
            case 69: attribute = "and-bitwise-assign"; break;
            case 70: attribute = "and-relational"; break;
            case 71: attribute = "and-bitwise"; break;
            case 73: attribute = "or-bitwise-assign"; break;
            case 74: attribute = "or-relational"; break;
            case 75: attribute = "or-bitwise"; break;
            case 77: attribute = "equal-relational"; break;
            case 78: attribute = "equal"; break;
            case 80: attribute = "diff-relational"; break;
            case 81: attribute = "not-relational"; break;
            case 84: attribute = "right-shift-assign"; break;
            case 85: attribute = "right-shift"; break;
            case 86: attribute = "greater-than-or-equal"; break;
            case 87: attribute = "greater-than"; break;
            case 90: attribute = "left-shift-assign"; break;
            case 91: attribute = "left-shift"; break;
            case 92: attribute = "less-than-or-equal"; break;
            case 93: attribute = "less-than"; break;
            case 94: attribute = "not-bitwise"; break;
            case 95: attribute = "error-unknown-char"; break;
            case 103: attribute = "octal-int"; break;
            case 105: attribute = "error-invalid-int-constant"; break;
            case 109: attribute = "error-invalid-octal-int"; break;
            case 112: attribute = "error-invalid-float-constant"; break;
            case 132:
            case 113: attribute = "float-constant"; break;
            case 115: attribute = "int-constant"; break;
            case 119: attribute = "error-invalid-hexa-int"; break;
            case 120: attribute = "hexa-int"; break;
            case 124: attribute = "error-invalid-binary-int"; break;
            case 125: attribute = "binary-int"; break;
            case 127: attribute = "dots"; break;
            case 128: attribute = "number-0"; break;
            case 130: attribute = "floating-point-constant"; break;
            case 201: attribute = "identifier"; break;
            case 500: attribute = "keyword"; break;
        }
        return attribute;
    }
}
