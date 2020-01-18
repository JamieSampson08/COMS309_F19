package com.iastate.eventXpert.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.iastate.eventXpert.OkHttpHandler;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.objects.Error;
import com.iastate.eventXpert.objects.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;

public class UploadImageActivity extends AppCompatActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.submit_button)
	Button submitButton;

	@BindView(R.id.upload_image_button)
	Button uploadImageButton;

	@BindView(R.id.image_to_upload)
	ImageView imageToUpload;

	@BindView(R.id.cancel_button)
	Button cancelButton;

	User user;
	Error error;
	String imageLocation;
	File image;

	private final int REQUEST_IMAGE_FROM_GALLERY = 200;
	private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");

	// storage permissions
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_image);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		user = (User) getIntent().getSerializableExtra("user");
		submitButton.setVisibility(View.GONE);
		verifyStoragePermissions(this);
	}

	/**
	 * Checks if the app has permission to write to device storage
	 *
	 * If the app does not has permission then the user will be prompted to grant permissions
	 */
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					activity,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			);
		}
	}

	@OnClick(R.id.cancel_button)
	public void cancelUpload() {
		goBackToUserDetails();
	}

	public void goBackToUserDetails() {
		Intent userDetails = new Intent(this, UserDetailsActivity.class);
		startActivity(userDetails);
	}

	@OnClick(R.id.upload_image_button)
	public void pickImageFromDeviceGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, REQUEST_IMAGE_FROM_GALLERY);
	}

	@OnClick(R.id.submit_button)
	public void saveImage() {
		String url = BASE_URL + "/files/" + user.getId();
		try {
			String imageName = UUID.randomUUID() + ".jpeg";
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("file", imageName, RequestBody.create(MEDIA_TYPE_JPEG, image))
					.build();

			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();

			Object response = okHttpHandler.execute(request).get();
			if (response.toString().indexOf("error") != -1) {
				error = new Error(response.toString());
				Toast.makeText(getApplicationContext(), error.getFullMessage(), Toast.LENGTH_LONG).show();
			} else {
				user = new User(response.toString());
				goBackToUserDetails();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			String errorMsg = "Error: InterruptedException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			e.printStackTrace();
			String errorMsg = "Error: ExecutionException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_FROM_GALLERY && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			imageLocation = cursor.getString(columnIndex);
			cursor.close();

			submitButton.setVisibility(View.VISIBLE);
			image = new File(imageLocation);
			Glide.with(this).load(imageLocation).into(imageToUpload);
		}

	}
}
