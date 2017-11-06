package Model.Teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import edu.plan9.naseemdev.naseem.Assign_Group;
import edu.plan9.naseemdev.naseem.Draw;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.SignUpSignIn;
import edu.plan9.naseemdev.naseem.Student_Attempts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.Test_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by DELL on 9/20/2017.
 */

public class Custom_Teacher_Test_Dialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
    private Button edit, pdf,assign , st_Attempts , delete, cancel;
    private Test_BO tests;
    private TextView name;
    Session session;
    ProgressDialog progressDialog;
    PdfPTable table;
    Document document;

    TransferUtility transferUtility;
    AmazonS3Client s3Client;
    public Custom_Teacher_Test_Dialog(Activity a, Test_BO u){
        super(a);
        activity = a;
        tests = u;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_teacher_test);
        session = new Session(activity);
        progressDialog = new ProgressDialog(activity);
        edit = (Button)findViewById(R.id.dialog_edit);
        assign = (Button)findViewById(R.id.dialog_assign);
        st_Attempts = (Button)findViewById(R.id.dialog_st_attempts);
        delete = (Button)findViewById(R.id.dialog_delete);
        cancel = (Button)findViewById(R.id.dialog_cancel);
        pdf = (Button)findViewById(R.id.dialog_pdf);
        edit.setOnClickListener(this);
        assign.setOnClickListener(this);
        st_Attempts.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        pdf.setOnClickListener(this);
        name = (TextView)findViewById(R.id.name);
        name.setText(tests.getName());

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_edit:{
               // Toast.makeText(getContext() , "Edit" , Toast.LENGTH_LONG).show();
                Intent it = new Intent(activity , New_Test.class);
                Bundle b = new Bundle();
                b.putString("Edit" , "1");
                b.putSerializable("Test" , tests);
                it.putExtras(b);
                activity.startActivity(it);
                break;
            }
            case R.id.dialog_pdf:{
                // Toast.makeText(getContext() , "Edit" , Toast.LENGTH_LONG).show();
                progressDialog.setMessage("!!!Creating PDF!!!");
                progressDialog.show();
                getShowTest();
                break;
            }
            case R.id.dialog_assign:{
                Toast.makeText(getContext() , "Assign Student" , Toast.LENGTH_LONG).show();
                Intent it = new Intent(activity , Assign_Group.class);
                it.putExtra("test_id" , ""+tests.getId());
                activity.startActivity(it);
                this.dismiss();
                break;
            }
            case R.id.dialog_st_attempts:{
                Toast.makeText(getContext() , "Student Attempts" , Toast.LENGTH_LONG).show();
                Intent it = new Intent(activity , Student_Attempts.class);
                it.putExtra("test_id" , ""+tests.getId());
                activity.startActivity(it);
                this.dismiss();
                break;
            }
            case R.id.dialog_delete:{
                progressDialog.setMessage("Deleting Test");
                progressDialog.show();
                deleteTest();
               /// Intent it = new Intent(activity , Draw.class);
                //activity.startActivity(it);
               // this.dismiss();
               // break;
            }
            case R.id.dialog_cancel:{

                dismiss();
                break;
            }
            default:{
                dismiss();
            }
        }
    }

    private void createPdf(final Complete_Test_BO complete_test_bo) throws FileNotFoundException, DocumentException {

        if(complete_test_bo != null) {
            File pdfFolder = new File(Environment.getExternalStorageDirectory(), "PDF");
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
            }

            Paragraph paragraph = new Paragraph();
            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
            File myFile = new File(pdfFolder.getAbsolutePath()+ tests.getName() + timeStamp + ".pdf");


            OutputStream output = new FileOutputStream(myFile);

            //Step 1
            document = new Document();

            //Step 2
            PdfWriter.getInstance(document, output);

            //Step 3
            document.open();
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            document.add(new Paragraph(Chunk.NEWLINE));
            Chunk testName = new Chunk(tests.getName() , font1);
            paragraph =new Paragraph();
            paragraph.add(testName);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(new Paragraph(Chunk.NEWLINE));
            Chunk text = new Chunk("Text Questions" , font1);
            paragraph = new Paragraph();
            paragraph.add(text);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(new Paragraph(Chunk.NEWLINE));

            Chunk chunk;
            if(complete_test_bo.getText_questions().size() != 0) {
                //Step 4 Add content
                for (int i = 0; i < complete_test_bo.getText_questions().size(); i++) {
                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.addCell(getCell("Q"+(i+1)+": "+complete_test_bo.getText_questions().get(i).getTitle(), PdfPCell.ALIGN_LEFT , 0));
                    table.addCell(getCell(String.valueOf("("+complete_test_bo.getText_questions().get(i).getMarks()+")"), PdfPCell.ALIGN_RIGHT , 0));
                    document.add(table);
                    document.add(new Paragraph("Ans: "));
                    document.add(new Paragraph(Chunk.NEWLINE));
                }
            }
            if(complete_test_bo.getSingleChoiceQuestions().size() != 0) {
                document.add(new Paragraph(Chunk.NEWLINE));
                Chunk single = new Chunk("Single Choice Question" , font1);
                paragraph = new Paragraph();
                paragraph.add(single);
                paragraph.setFont(font1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(Chunk.NEWLINE));
                for (int i = 0; i < complete_test_bo.getSingleChoiceQuestions().size(); i++) {
                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.addCell(getCell("Q"+(i+1)+": "+complete_test_bo.getSingleChoiceQuestions().get(i).getTitle(), PdfPCell.ALIGN_LEFT , 0));
                    table.addCell(getCell(String.valueOf("("+complete_test_bo.getSingleChoiceQuestions().get(i).getMarks()+")"), PdfPCell.ALIGN_RIGHT , 0));
                    document.add(table);
                    for (int j = 0; j < complete_test_bo.getSingleChoiceQuestions().get(i).getOptions().size(); j++) {
                        document.add(new Paragraph((j+1) + ") " + complete_test_bo.getSingleChoiceQuestions().get(i).getOptions().get(j).getOption()));
                    }
                    document.add(new Paragraph(Chunk.NEWLINE));
                }
            }
            if(complete_test_bo.getMultipleChoiceQuestions().size() != 0) {
                document.add(new Paragraph(Chunk.NEWLINE));
                Chunk multi = new Chunk("Multi Choice Question" , font1);
                paragraph = new Paragraph();
                paragraph.add(multi);
                paragraph.setFont(font1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(Chunk.NEWLINE));
                for (int i = 0; i < complete_test_bo.getMultipleChoiceQuestions().size(); i++) {
                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.addCell(getCell("Q"+(i+1)+": "+complete_test_bo.getMultipleChoiceQuestions().get(i).getTitle(), PdfPCell.ALIGN_LEFT , 0));
                    table.addCell(getCell(String.valueOf("("+complete_test_bo.getMultipleChoiceQuestions().get(i).getMarks()+")"), PdfPCell.ALIGN_RIGHT , 0));
                    document.add(table);
                    for (int j = 0; j < complete_test_bo.getMultipleChoiceQuestions().get(i).getOptions().size(); j++) {
                        document.add(new Paragraph((j+1) + ") " + complete_test_bo.getMultipleChoiceQuestions().get(i).getOptions().get(j).getOption()));
                    }
                    document.add(new Paragraph(Chunk.NEWLINE));
                }
            }
            if(complete_test_bo.getParagraphQuestions().size() != 0) {
                document.add(new Paragraph(Chunk.NEWLINE));
                Chunk para = new Chunk("Paragraph Question" , font1);
                paragraph = new Paragraph();
                paragraph.add(para);
                paragraph.setFont(font1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(Chunk.NEWLINE));
                for (int i = 0; i < complete_test_bo.getParagraphQuestions().size(); i++) {
                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.addCell(getCell("Q"+(i+1)+": "+complete_test_bo.getParagraphQuestions().get(i).getTitle(), PdfPCell.ALIGN_LEFT , 0));
                    table.addCell(getCell(String.valueOf("("+complete_test_bo.getParagraphQuestions().get(i).getMarks()+")"), PdfPCell.ALIGN_RIGHT , 0));
                    document.add(table);
                    document.add(new Paragraph(Chunk.NEWLINE));
                    document.add(new Paragraph(Chunk.NEWLINE));
                    document.add(new Paragraph(Chunk.NEWLINE));
                    document.add(new Paragraph(Chunk.NEWLINE));
                }
            }
            if(complete_test_bo.getBooleanQuestions().size() != 0) {
                document.add(new Paragraph(Chunk.NEWLINE));
                Chunk tf = new Chunk("True/False Question" , font1);
                paragraph = new Paragraph();
                paragraph.add(tf);
                paragraph.setFont(font1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(Chunk.NEWLINE));
                for (int i = 0; i < complete_test_bo.getParagraphQuestions().size(); i++) {
                    table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.addCell(getCell("Q"+(i+1)+": "+complete_test_bo.getBooleanQuestions().get(i).getTitle(), PdfPCell.ALIGN_LEFT , 0));
                    table.addCell(getCell(String.valueOf("("+complete_test_bo.getBooleanQuestions().get(i).getMarks()+")"), PdfPCell.ALIGN_RIGHT , 0));
                    document.add(table);
                }
            }

            if(complete_test_bo.getImagesQuestions().size() != 0) {
                document.add(new Paragraph(Chunk.NEWLINE));
                Chunk tf = new Chunk("Image Questions" , font1);
                paragraph = new Paragraph();
                paragraph.add(tf);
                paragraph.setFont(font1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(Chunk.NEWLINE));
                for (int i = 0; i < complete_test_bo.getImagesQuestions().size(); i++) {

                    final int j = i;
                    credentialsProvider();
                    transferUtility = new TransferUtility(s3Client, activity.getApplicationContext());
                    File file;
                    file = new File(Environment.getExternalStorageDirectory().toString() + "/" + complete_test_bo.getImagesQuestions().get(i).getImageKey());
                    complete_test_bo.getImagesQuestions().get(i).setImage(file);
                    TransferObserver observer = transferUtility.download(
                                "naseemedu",     /* The bucket to upload to */
                                complete_test_bo.getImagesQuestions().get(i).getImageKey(),    /* The key for the uploaded object */
                                complete_test_bo.getImagesQuestions().get(i).getImage()       /* The file where the data to upload exists */
                    );
                        //test_object.getImagesQuestions().get(i).setImage(file);
                    observer.setTransferListener(new TransferListener() {
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if(state.toString() == "COMPLETED"){
                                //Toast.makeText(getApplicationContext() , "Successed" , Toast.LENGTH_LONG).show();
                                //getShowTest();
                                //save_test(j);
                                try {
                                    table = new PdfPTable(2);
                                    table.setWidthPercentage(100);
                                    table.addCell(getCell("Q" + (j + 1) + ": " + complete_test_bo.getImagesQuestions().get(j).getTitle(), PdfPCell.ALIGN_LEFT, 0));
                                    table.addCell(getCell(String.valueOf("(" + complete_test_bo.getImagesQuestions().get(j).getMarks() + ")"), PdfPCell.ALIGN_RIGHT, 0));
                                    document.add(table);
                                    document.add(new Paragraph(Chunk.NEWLINE));

                                    Bitmap bitmap = BitmapFactory.decodeFile(complete_test_bo.getImagesQuestions().get(j).getImage().getPath());
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                                    Image image = Image.getInstance(stream.toByteArray());
                                    document.add(image);
                                }catch (IOException e){


                                }catch (DocumentException e){

                                }

                            }
                        }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                            }

                            @Override
                            public void onError(int id, Exception ex) {

                            }
                        });
                 /*  try {
                        String fileName = "newfile";

                        File file = File.createTempFile(fileName, null, activity.getCacheDir());
                       //FileOutputStream fileOutputStream = new FileOutputStream(complete_test_bo.getImagesQuestions().get(i).getImage());

                        copy(complete_test_bo.getImagesQuestions().get(i).getImage() , file);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        Image image = Image.getInstance(stream.toByteArray());
                        document.add(image);
                    }catch (IOException e){

                    }*/
                }
            }
            //Step 5: Close the document
            document.close();
            progressDialog.dismiss();
            viewPdf(myFile);
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(activity.getApplicationContext() , "Test is Empty" , Toast.LENGTH_LONG).show();
        }
    }

    private void viewPdf(File myFile){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }

    public void getShowTest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EditTest + session.getAuthenticationToken(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            if(s.equals("-1")){
                                session.destroy_session();
                                progressDialog.dismiss();
                                Intent it = new Intent(activity , SignUpSignIn.class);
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                activity.startActivity(it);
                                return;
                            }
                            if(s.equals("-2")){
                                hide();
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            JsonParsor js = new JsonParsor();
                            Complete_Test_BO test_object = js.parseShow_Test(s.toString());
                            if(test_object != null) {
                                try{
                                    createPdf(test_object);
                                }catch (FileNotFoundException e){
                                    progressDialog.dismiss();
                                    Toast.makeText(activity.getApplicationContext() , ""+e.toString() , Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                hide();
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Cannot_Load_Test, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }catch (Exception e){
                            hide();
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error + e.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hide();
                progressDialog.dismiss();
                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put("test_id",String.valueOf(tests.getId()));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void deleteTest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Delete_Test + session.getAuthenticationToken(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            if(s.equals("-1")){
                                session.destroy_session();
                                progressDialog.dismiss();
                                Intent it = new Intent(activity , SignUpSignIn.class);
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                activity.startActivity(it);
                                return;
                            }
                            if(s.equals("-2") || s.equals("-3")){
                                hide();
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            progressDialog.dismiss();

                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Successfully Delete Test", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }catch (Exception e){
                            hide();
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error + e.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hide();
                progressDialog.dismiss();
                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put("test_id",String.valueOf(tests.getId()));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public PdfPCell getCell(String text, int alignment , int padding) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public void credentialsProvider(){

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                activity.getApplicationContext(),
                "us-west-2:687a499c-965c-4185-9b6a-8e72af76cf36", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        setAmazonS3Client(credentialsProvider);
    }

    public void setAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider){

        // Create an S3 client
        s3Client = new AmazonS3Client(credentialsProvider);

        // Set the region of your S3 bucket
        s3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));

    }
}

