package com.example.drawingfun;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;




public class MainActivity extends Activity implements OnClickListener{
	
	private DrawingView drawView;
	private ImageButton currPaint, drawBtn, newBtn, saveBtn;
	private float smallBrush, mediumBrush, largeBrush;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		drawView = (DrawingView)findViewById(R.id.drawing);
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
		currPaint = (ImageButton)paintLayout.getChildAt(0);
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
		smallBrush = getResources().getInteger(R.integer.small_size);
		smallBrush = getResources().getInteger(R.integer.medium_size);
		smallBrush = getResources().getInteger(R.integer.large_size);
//		drawBtn = (ImageButton)findViewById(R.id.draw_btn);
//		drawBtn.setOnClickListener(this);
		newBtn = (ImageButton)findViewById(R.id.new_btn);
		newBtn.setOnClickListener(this);
		saveBtn = (ImageButton)findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view){
//		if(view.getId()==R.id.draw_btn){
		    //draw button clicked
//		}
		if(view.getId()==R.id.new_btn){
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New drawing");
			newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        drawView.startNew();
			        dialog.dismiss();
			    }
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			newDialog.show();
			}
		else if(view.getId()==R.id.save_btn){
			AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
			saveDialog.setTitle("Save drawing");
			saveDialog.setMessage("Save drawing to device Gallery?");
			saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
					drawView.setDrawingCacheEnabled(true);
					String imgSaved = MediaStore.Images.Media.insertImage(
						    getContentResolver(), drawView.getDrawingCache(),
						    UUID.randomUUID().toString()+".png", "drawing");
					if(imgSaved!=null){
					    Toast savedToast = Toast.makeText(getApplicationContext(),
					        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
					    savedToast.show();
					}
					else{
					}
					drawView.destroyDrawingCache();
			    }
			});
			saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			saveDialog.show();
			
			}
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void paintClicked(View view){
		
		if (view!=currPaint){
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			drawView.setColor(color);
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint = (ImageButton) view;
			
		}
	}

}
