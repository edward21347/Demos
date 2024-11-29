package com.example;

public class WordTest {
    private final float CM = 28.35f;

//    @Test
//    public void test(){
//        // 创建一个新的Word文档
//        Document document = new Document();
//        Section section1 = document.addSection();
//
//        createOneTableWithTextBox(section1, "D:\\1cm.docx", 2 * CM);
//
//        createOneTableWithTextBox(section1, "D:\\3cm.docx", 5 * CM);
//
//        // 添加新的一页
//
//        Section section2 = document.addSection();
//        createOneTableWithTextBox(section2, "D:\\1cm.docx", 2 * CM);
//
//        createOneTableWithTextBox(section2, "D:\\3cm.docx", 5 * CM);
//
//        // 保存文档
//        String outputPath = "D:\\output.docx";
//        document.saveToFile(outputPath, FileFormat.Docx);
//        System.out.println("文档创建成功，保存到：" + outputPath);
//    }
//
//    private void createOneTableWithTextBox(Section section, String template, float horizontalPos){
//        // Load the template document
//        Document templateDoc = new Document();
//        templateDoc.loadFromFile(template);
//
//        // Extract the table from the template document
//        Section templateSection = templateDoc.getSections().get(0);
//        Table table = null;
//        for (Object obj : templateSection.getTables()) {
//            if (obj instanceof Table) {
//                table = (Table) obj;
//                break;
//            }
//        }
//
//        // 创建第一个文本框
//        TextBox textBox = new TextBox(section.getDocument());
//        textBox.getFormat().setWidth(3.5f * CM); // 设置宽度：3.5cm
//        textBox.getFormat().setHeight(28.5f * CM); // 设置高度：28.5cm
//        textBox.getFormat().setHorizontalOrigin(HorizontalOrigin.Page);
//        textBox.getFormat().setVerticalOrigin(VerticalOrigin.Page);
//        textBox.getFormat().setHorizontalPosition(horizontalPos); // 设置文本框的水平位置（可以调整）
//        textBox.getFormat().setVerticalPosition(CM); // 设置文本框的垂直位置（可以调整）
//        textBox.getFormat().setLineColor(Color.WHITE); // 设置文本框无边框颜色
//
//        // 向第二个文本框中添加文本
//        Table newTable = (Table) table.deepClone();
//        textBox.getBody().getChildObjects().add(newTable);
//
//        // 将文本框添加到文档的段落中
//        section.addParagraph().getChildObjects().add(textBox);
//    }
}
