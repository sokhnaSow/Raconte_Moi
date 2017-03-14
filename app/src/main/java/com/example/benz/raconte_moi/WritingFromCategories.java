package com.example.benz.raconte_moi;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.DAO;
import com.example.benz.raconte_moi.DAO.History;
import com.example.benz.raconte_moi.DAO.Illustration;
import com.example.benz.raconte_moi.DAO.Image;
import com.example.benz.raconte_moi.DAO.Writing;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WritingFromCategories extends AppCompatActivity implements View.OnClickListener {

    ImageView  imageView;
    DAO d;
    View rowView = null;
    private String idChild;
    String categorie;
    private int positionItem;
    private ImageButton uploadBtn, saveBtn, update_btn2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    private String idWriting="";
    private String idIllustration="";
    private String pathImage="";
    private HashMap<String, String> writingHistory = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_from_categories);
        imageView = (ImageView) findViewById(R.id.image);
        categorie = getIntent().getStringExtra("categorie");
        FirebaseStorage storage;
        StorageReference storageRef;
        storage = FirebaseStorage.getInstance();

        // upload button
        uploadBtn = (ImageButton) findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        //save button
        update_btn2 = (ImageButton) findViewById(R.id.update_btn2);
        update_btn2.setOnClickListener(this);

        Intent intent = getIntent();
        idChild = intent.getStringExtra("idChild");
        
        storageRef = storage.getReference();
        StorageReference ref = storageRef.child("Cartegories/"+ categorie+"/1.jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.save_btn) {
            if (!idIllustration.equals("") && !pathImage.equals("")) {
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("paragraphe", ((EditText) findViewById(R.id.paragraph)).getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Illustration").child(idIllustration).updateChildren(taskMap);

                // save image
                final EditText para = (EditText) findViewById(R.id.paragraph);
                if (para.getText().toString().trim().length() != 0) {
                    AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                    saveDialog.setTitle("Save drawing");
                    saveDialog.setMessage("Save drawing to device Gallery?");
                    saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //save drawing
                            imageView.setDrawingCacheEnabled(true);
                            //attempt to save

                            String imgSaved = MediaStore.Images.Media.insertImage(
                                    getContentResolver(), imageView.getDrawingCache(),
                                    pathImage, "drawing");

                            //feedback
                            if (imgSaved != null) {

                                Toast savedToast = Toast.makeText(getApplicationContext(),
                                        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                                savedToast.show();

                                final DAO dao = new DAO();
                                System.out.println(pathImage);
                                dao.deleteImage(imageView.getDrawingCache(), pathImage);
                                final String idImg = dao.addImage(imageView.getDrawingCache(), pathImage);


                            } else {
                                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                        "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                                unsavedToast.show();
                            }
                            imageView.destroyDrawingCache();
                        }
                    });
                    saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    saveDialog.show();
                } else {
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! you should write a story.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }

            } else {

            //save drawing
            final EditText para = (EditText) findViewById(R.id.paragraph);
            if (para.getText().toString().trim().length() != 0) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                saveDialog.setTitle("Save drawing");
                saveDialog.setMessage("Save drawing to device Gallery?");
                saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save drawing
                        imageView.setDrawingCacheEnabled(true);
                        //attempt to save
                        String path = UUID.randomUUID().toString() + ".png";
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), imageView.getDrawingCache(),
                                path, "drawing");
                        String uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI.getPath() + "/" + path;

                        //feedback
                        if (imgSaved != null) {

                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            savedToast.show();

                            final DAO dao = new DAO();

                            final String idImg = dao.addImage(imageView.getDrawingCache(), "Images/"+idChild + "/" + path);

                            final String text = ((EditText) findViewById(R.id.paragraph)).getText().toString();

                            final Dialog dialoge = new Dialog(WritingFromCategories.this);
                            dialoge.setContentView(R.layout.layout_enter_titlte_history);
                            final EditText title = (EditText) dialoge.findViewById(R.id.title);

                            dialoge.setTitle("Enter title");
                            dialoge.show();
                            Button dialogButtonAddTtitle = (Button) dialoge.findViewById(R.id.okBtn_enterTitle);
                            Button dialogButtondCancle = (Button) dialoge.findViewById(R.id.cancelBtn);
                            dialogButtondCancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialoge.dismiss();
                                }
                            });
                            dialogButtonAddTtitle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d = new DAO();
                                    History h = new History(title.getText().toString());
                                    String keyH = d.addHistory(h);
                                    Illustration i = new Illustration(idImg, keyH, text);
                                    dao.addIllustration(i);
                                    Date currentDate = new Date(System.currentTimeMillis());
                                    Writing w = new Writing(idChild, keyH, 0, 0, false, categorie,currentDate );
                                    d.addWriting(w);
                                    idWriting = d.addWriting(w);
                                    dialoge.dismiss();
                                }
                            });


                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        imageView.destroyDrawingCache();
                    }
                });
                saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                saveDialog.show();
            } else {
                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Oops! you should write a story.", Toast.LENGTH_SHORT);
                unsavedToast.show();
            }
        }
        }

        else {
            if (view.getId() == R.id.upload_btn) {
                final EditText par =  ((EditText) findViewById(R.id.paragraph));
                par.setFocusableInTouchMode(true);
                final ArrayList<History> items = new ArrayList<History>();
                final HashMap<String, String> result = new HashMap<String, String>();
                // get all title history of one kids
                final ArrayList<String> titlesId = new ArrayList<String>();
                // HashMap<Titles, Keys>
                final HashMap<String, String> titles = new HashMap<String, String>();
                refData.child("Writing").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("count", "" + dataSnapshot.getChildrenCount());


                        for (DataSnapshot writing : dataSnapshot.getChildren()) {
                            //  writing.get
                            Writing w = writing.getValue(Writing.class);
                            if (w.getIdChild().equals(idChild) && !w.isValide() && w.getCategorie().equals(categorie)) {
                                titlesId.add(w.getIdHistory());
                                writingHistory.put(w.getIdHistory(),writing.getKey());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                refData.child("history").addListenerForSingleValueEvent(new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("count", "" + dataSnapshot.getChildrenCount());

                        int i = 0;
                        for (DataSnapshot h : dataSnapshot.getChildren()) {

                            String w = h.getKey();
                            History v = h.getValue(History.class);
                            for (String t : titlesId) {
                                if (w.equals(t)) {
                                    titles.put(v.getTitle(), w);
                                    result.put(v.getTitle(), w);

                                    History hi = new History(v.getTitle());

                                    items.add(hi);
                                    i++;
                                    //return titles;
                                }

                            }// end of for
                        }
                        WritingFromCategories.TitleAdapter itemsAdapter;
                        final Dialog dialog = new Dialog(WritingFromCategories.this);
                        dialog.setContentView(R.layout.layout_list_history);
                        itemsAdapter = new WritingFromCategories.TitleAdapter(WritingFromCategories.this, R.layout.item_history, items);
                        ListView listView = (ListView) dialog.findViewById(R.id.listHistory);
                        listView.setAdapter(itemsAdapter);
                        dialog.setTitle("History title");
                        dialog.show();
                        final String[] idImage = new String[1];
                        final String[] para = new String[1];
                        Button dialogButtonAdd = (Button) dialog.findViewById(R.id.okBtn);
                        Button dialogButtondCancle = (Button) dialog.findViewById(R.id.cancelBtn);
                        dialogButtondCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });
                        dialogButtonAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                final StorageReference storageRef = storage.getReference();
                                refData.child("Illustration").addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.e("count", "" + dataSnapshot.getChildrenCount());
                                        Log.e("count", "" + dataSnapshot.getChildrenCount());


                                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                                            Illustration w = i.getValue(Illustration.class);
                                            if (w.getIdHistory().equals(result.get(items.get(positionItem).getTitle()))) {
                                                idIllustration = i.getKey();
                                                idImage[0] = w.getIdImage();
                                                para[0] = w.getParagraphe();
                                                final EditText text = ((EditText) findViewById(R.id.paragraph));
                                                text.setText(para[0]);
                                                idWriting=writingHistory.get(w.getIdHistory());

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                });

                                refData.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.e("count", "" + dataSnapshot.getChildrenCount());


                                        for (DataSnapshot im : dataSnapshot.getChildren()) {

                                            String w = im.getKey();
                                            if (w.equals(idImage[0])) {
                                                pathImage=im.getValue(Image.class).getPathImage();
                                                String path = im.getValue(Image.class).getPathImage();
                                                StorageReference ref = storageRef.child(path);
                                                final long ONE_MEGABYTE = 1024 * 1024;
                                                ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                    @Override
                                                    public void onSuccess(byte[] bytes) {
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                        Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                                                       imageView.setImageBitmap(drawableBitmap);


                                                    }
                                                });

                                                Toast.makeText(WritingFromCategories.this, "Upload successfully", Toast.LENGTH_LONG).show();

                                                dialog.dismiss();


                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }


                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }


            else { if (view.getId()==R.id.update_btn2) {
                if (!idIllustration.equals("")){
                    Map<String,Object> taskMap = new HashMap<String,Object>();
                    taskMap.put("paragraphe", ((EditText) findViewById(R.id.paragraph)).getText().toString() );
                    FirebaseDatabase.getInstance().getReference().child("Illustration").child(idIllustration).updateChildren(taskMap);
                }

                if (idWriting.equals("")){
                    //save drawing
                    final EditText para = (EditText) findViewById(R.id.paragraph);
                    if (para.getText().toString().trim().length() != 0) {
                        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                        saveDialog.setTitle("Save&validate drawing");
                        saveDialog.setMessage("Save&validate drawing to device Gallery?");
                        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //save drawing
                                imageView.setDrawingCacheEnabled(true);
                                //attempt to save
                                String path = UUID.randomUUID().toString() + ".png";
                                String imgSaved = MediaStore.Images.Media.insertImage(
                                        getContentResolver(), imageView.getDrawingCache(),
                                        path, "drawing");
                                String uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI.getPath() + "/" + path;

                                //feedback
                                if (imgSaved != null) {

                                    Toast savedToast = Toast.makeText(getApplicationContext(),
                                            "Drawing Saved&validate to Gallery!", Toast.LENGTH_SHORT);
                                    savedToast.show();

                                    final DAO dao = new DAO();

                                    final String idImg = dao.addImage(imageView.getDrawingCache(),"Images/" +idChild+"/"+path);

                                    final String text = ((EditText) findViewById(R.id.paragraph)).getText().toString();

                                    final Dialog dialoge = new Dialog(WritingFromCategories.this);
                                    dialoge.setContentView(R.layout.layout_enter_titlte_history);
                                    final EditText title = (EditText) dialoge.findViewById(R.id.title);

                                    dialoge.setTitle("Enter title");
                                    dialoge.show();
                                    Button dialogButtonAddTtitle = (Button) dialoge.findViewById(R.id.okBtn_enterTitle);
                                    Button dialogButtondCancle = (Button) dialoge.findViewById(R.id.cancelBtn);
                                    dialogButtondCancle.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialoge.dismiss();
                                        }
                                    });
                                    dialogButtonAddTtitle.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            d = new DAO();
                                            History h = new History(title.getText().toString());
                                            String keyH = d.addHistory(h);
                                            Illustration i = new Illustration(idImg, keyH, text);
                                            dao.addIllustration(i);
                                            Date currentDate = new Date(System.currentTimeMillis());
                                            Writing w = new Writing(idChild, keyH, 0, 0, true, categorie,currentDate);
                                            final EditText par =  ((EditText) findViewById(R.id.paragraph));
                                            d.addWriting(w);
                                            par.setFocusable(false);
                                            idWriting = d.addWriting(w);
                                            dialoge.dismiss();
                                        }
                                    });


                                } else {
                                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                                    unsavedToast.show();
                                }
                                imageView.destroyDrawingCache();
                            }
                        });
                        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        saveDialog.show();
                    } else {
                        Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                "Oops! you should write a story.", Toast.LENGTH_SHORT);
                        unsavedToast.show();
                    }
                }
                else {
                    Map<String,Object> taskMap = new HashMap<String,Object>();
                    taskMap.put("valide", true);
                    System.out.println(idWriting);
                    FirebaseDatabase.getInstance().getReference().child("Writing").child(idWriting).updateChildren(taskMap);
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Story validate", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }

                idWriting="";
                pathImage="";
                idIllustration="";
            }

            }
        }
    }

    // adtateur paersonalizer
    public class TitleAdapter extends ArrayAdapter<History> {


        public TitleAdapter(Context context, int resource, ArrayList<History> items) {
            super(context, resource, items);
        }

        public TitleAdapter(Context context, ArrayList<History> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position

            History kid = getItem(position);
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View v= convertView;
            // Check if an existing view is being reused, otherwise inflate the view

            if (null == convertView) {
                rowView = inflater.inflate(R.layout.item_history, parent, false);
            } else {
                // PAS TOUJOURS ADAPTE AUX BESOINS //
                // IL FAUDRA PARFOIS TOUJOURS JETER LE convertViewFOURNI
                rowView = convertView;
            }
            if (position % 2 == 0)
                rowView.setBackgroundColor(Color.LTGRAY);
            else rowView.setBackgroundColor(Color.WHITE);
            // Lookup view for data population
            TextView tvName = (TextView) rowView.findViewById(R.id.titleTextView);
            // Populate the data into the template view using the data object
            tvName.setText(kid.getTitle());


            rowView.setTag(position);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                /// go to activity manager
                public void onClick(View view) {

                    int position = (Integer) view.getTag();


                    //dialog.dismiss();

                }
            });
            positionItem = position;
            return rowView;
        }
    }

}

