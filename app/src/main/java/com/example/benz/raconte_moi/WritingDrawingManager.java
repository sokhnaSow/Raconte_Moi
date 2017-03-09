
        package com.example.benz.raconte_moi;


        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Picture;
        import android.graphics.PorterDuff;
        import android.graphics.RectF;
        import android.net.Uri;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.provider.Settings;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.bumptech.glide.Glide;
        import com.example.benz.raconte_moi.DAO.Child;
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

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.net.URI;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Objects;
        import java.util.UUID;

public class WritingDrawingManager extends AppCompatActivity implements View.OnClickListener {
    View rowView = null;
    final Dialog dialog = null;

    //custom drawing view
    private DrawingView drawView;
    //buttons
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, uploadBtn, saveBtn, opacityBtn;
    //sizes
    private float smallBrush, mediumBrush, largeBrush;
    private DAO d;
    String idChild = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    private int positionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_drawing_manager);
//get drawing view
        drawView = (DrawingView) findViewById(R.id.drawing);

        //get the palette and first color button
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        //sizes from dimensions
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        //draw button
        drawBtn = (ImageButton) findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        //set initial size
        drawView.setBrushSize(mediumBrush);

        //erase button
        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton) findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        // upload button
        uploadBtn = (ImageButton) findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        //opacity
        opacityBtn = (ImageButton) findViewById(R.id.opacity_btn);
        opacityBtn.setOnClickListener(this);
        this.d = new DAO();
        Intent intent = getIntent();
        idChild = intent.getStringExtra("idChild");
        //drawView.draw(new Canvas(d.searchImage("zz")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //user clicked paint
    public void paintClicked(View view) {
        //use chosen color

        //set erase false
        drawView.setErase(false);
        drawView.setPaintAlpha(100);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //update ui
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.draw_btn) {
            //draw button clicked
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            //listen for clicks on size buttons
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            //show and wait for user interaction
            brushDialog.show();
        } else if (view.getId() == R.id.erase_btn) {
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            //size buttons
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        } else if (view.getId() == R.id.new_btn) {
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    final EditText text = ((EditText) findViewById(R.id.paragraph));
                    text.setText("");
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        } else if (view.getId() == R.id.save_btn) {
            //save drawing
            final EditText para = (EditText) findViewById(R.id.paragraph);
            if (para.getText().toString().trim().length() != 0) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                saveDialog.setTitle("Save drawing");
                saveDialog.setMessage("Save drawing to device Gallery?");
                saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //save drawing
                        drawView.setDrawingCacheEnabled(true);
                        //attempt to save
                        String path = UUID.randomUUID().toString() + ".png";
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), drawView.getDrawingCache(),
                                path, "drawing");
                        String uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI.getPath() + "/" + path;

                        //feedback
                        if (imgSaved != null) {

                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            savedToast.show();

                            final DAO dao = new DAO();

                            final String idImg = dao.addImage(drawView.getDrawingCache(), idChild+"/"+path, "Drawing");

                            final String text = ((EditText) findViewById(R.id.paragraph)).getText().toString();

                            final Dialog dialoge = new Dialog(WritingDrawingManager.this);
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
                                    History h = new History(title.getText().toString());
                                    String keyH = d.addHistory(h);
                                    Illustration i = new Illustration(idImg, keyH, text);
                                    dao.addIllustration(i);

                                    Writing w = new Writing(idChild, keyH, 0, 0);
                                    d.addWriting(w);
                                    dialoge.dismiss();
                                }
                            });


                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        drawView.destroyDrawingCache();
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
        } else if (view.getId() == R.id.opacity_btn) {
            //launch opacity chooser
            final Dialog seekDialog = new Dialog(this);
            seekDialog.setTitle("Opacity level:");
            seekDialog.setContentView(R.layout.opacity_chooser);
            //get ui elements
            final TextView seekTxt = (TextView) seekDialog.findViewById(R.id.opq_txt);
            final SeekBar seekOpq = (SeekBar) seekDialog.findViewById(R.id.opacity_seek);
            //set max
            seekOpq.setMax(100);
            //show current level
            int currLevel = drawView.getPaintAlpha();
            seekTxt.setText(currLevel + "%");
            seekOpq.setProgress(currLevel);
            //update as user interacts
            seekOpq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekTxt.setText(Integer.toString(progress) + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

            });
            //listen for clicks on ok
            Button opqBtn = (Button) seekDialog.findViewById(R.id.opq_ok);
            opqBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setPaintAlpha(seekOpq.getProgress());
                    seekDialog.dismiss();
                }
            });
            //show dialog
            seekDialog.show();
        } else {
            if (view.getId() == R.id.upload_btn) {
                System.out.println(d.getTitlesHistory(idChild).size());
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
                            if (w.getIdChild().equals(idChild)) {
                                titlesId.add(w.getIdHistory());


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
                                    //System.out.println(titles[i]);
                                    items.add(hi);
                                    i++;
                                    //return titles;
                                }

                            }// end of for
                        }
                        TitleAdapter itemsAdapter;
                        final Dialog dialog = new Dialog(WritingDrawingManager.this);
                        dialog.setContentView(R.layout.layout_list_history);
                        itemsAdapter = new TitleAdapter(WritingDrawingManager.this, R.layout.item_history, items);
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

                                System.out.println((items.get(positionItem).getTitle()));
                                System.out.println(result.get(items.get(positionItem).getTitle()));
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
                                                idImage[0] = w.getIdImage();
                                                para[0] = w.getParagraphe();
                                                final EditText text = ((EditText) findViewById(R.id.paragraph));
                                                text.setText(para[0]);

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
                                               String path = im.getValue(Image.class).getPathImage();
                                                StorageReference ref = storageRef.child(path);
                                        final long ONE_MEGABYTE = 1024 * 1024;
                                        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                                                drawView.upload(drawableBitmap);


                                            }
                                        });

                                        Toast.makeText(WritingDrawingManager.this, "Kid successfully added", Toast.LENGTH_LONG).show();

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
        }
    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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