package event_recycler;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Event;
import project03.csc296.pollinateapp.PhotoUtils;
import project03.csc296.pollinateapp.R;
import project03.csc296.pollinateapp.ViewEventActivity;

/**
 * Created by jhrebena on 12/9/15.
 */
public class EventHolder extends RecyclerView.ViewHolder {


    public TextView mName;
    public TextView mDate;
    public TextView mTime;

    public ImageView mPostedPic;
    private Event mEvent;

    private String mCurrUser;

    private static final String jl = "JEFF_VIEW_HOLDER";

    public EventHolder(View view, String currUser) {
        super(view);
        Log.d(jl, "View Holder Created");
        mName = (TextView) view.findViewById(R.id.text_event_recycler);
        mDate = (TextView) view.findViewById(R.id.text_date_recycler);
        mTime = (TextView) view.findViewById(R.id.text_time_recycler);
        mPostedPic = (ImageView) view.findViewById(R.id.image_event_recycler);
        mCurrUser = currUser;


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewEventActivity.class);
                i.putExtra("KEY_EVENT", mName.getText().toString());
                i.putExtra("KEY_EMAIL", mCurrUser);
                v.getContext().startActivity(i);
                Log.d(jl, "Holder Clicked!");
            }
        });
    }

    public void bind(Event e) {
        Log.d(jl, "Event view Holder Bound");
        mEvent = e;
//        mEmail.setText(DAO.getEmail(mFeedItem.getEmail()));
        mName.setText(mEvent.getTitle());
        mTime.setText(mEvent.getStartTime());
        mDate.setText(mEvent.getStartDate());

        Log.d(jl, "Event type is " + mEvent.getType());

        if (mEvent.getEventPic() != null) {
            mPostedPic.setImageBitmap(new PhotoUtils().getBitmapFromByte(mEvent.getEventPic()));
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