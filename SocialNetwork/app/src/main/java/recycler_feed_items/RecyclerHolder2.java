package recycler_feed_items;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import Database.DbDao;
import model.FeedItem;
import model.User;
import project2.csc296.socialnetwork.PhotoUtils;
import project2.csc296.socialnetwork.R;

/**
 * Created by jhrebena on 11/14/15.
 */
public class RecyclerHolder2 extends RecyclerView.ViewHolder {


    public TextView mEmail;
    public TextView mDate;
    public TextView mTime;

    public TextView mContent;
    public TextView mDatePosted;
    public ImageView mPostedPic;
    private FeedItem mFeedItem;

    private static final String jl = "JEFF_VIEW_HOLDER2";

    public RecyclerHolder2(View view) {
        super(view);
        Log.d(jl, "View Holder Created");
        mEmail = (TextView) view.findViewById(R.id.text_email);
        mDate = (TextView) view.findViewById(R.id.text_date);
        mTime = (TextView) view.findViewById(R.id.text_time);
        mContent =  (TextView) view.findViewById(R.id.text_content);
        mPostedPic = (ImageView) view.findViewById(R.id.image_picture);


//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppCompatActivity c = (AppCompatActivity)v.getContext();
//                FragmentManager manager = c.getSupportFragmentManager();
//                UserDialog pd = PlayerDialog.newInstance(mPlayer);
//                pd.show(manager, "AlbumDialog");
//                Log.d(jl, "Holder Clicked!");
//            }
//        });
    }

    public void bind(FeedItem f) {
        Log.d(jl, "View Holder Bound");
        mFeedItem = f;
//        mEmail.setText(DAO.getEmail(mFeedItem.getEmail()));
        mEmail.setText(mFeedItem.getEmail());
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
        String dayString = formatter1.format(new Date(Long.parseLong(mFeedItem.getPostDate())));
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
        String timeString = formatter2.format(new Date(Long.parseLong(mFeedItem.getPostDate())));

        mDate.setText(dayString);
        mTime.setText(timeString);
        mContent.setText(mFeedItem.getContent());
        if (mFeedItem.getPostedPic() != null) {
            mPostedPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(mFeedItem.getPostedPic()));
        }


    }


    //    private void bindPhoto() {
//
//        File picturesDir = Context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File mPictureFile = new File(picturesDir, mUser.getPhoto());
//        Uri photoUri = Uri.fromFile(mPictureFile);
//        if (mPictureFile == null || !mPictureFile.exists()) {
//
//        }
//        else {
//            Bitmap photo = new PhotoUtils().getScaledBitmap(mPictureFile.getPath(),
//                    mPicture.getWidth(), mPicture.getHeight());
//            mPicture.setImageBitmap(photo);
//
//        }
//    }



}