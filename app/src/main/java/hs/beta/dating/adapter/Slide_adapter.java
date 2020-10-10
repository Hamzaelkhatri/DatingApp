package hs.beta.dating.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import hs.beta.dating.R;
import hs.beta.dating.models.Slide;

public class Slide_adapter extends PagerAdapter
{

    List<Slide> mData;
    Activity mContext;

    public Slide_adapter(List<Slide> mData, Activity mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.pager_item,null);
        ImageView slideImage = slideLayout.findViewById(R.id.image);
        TextView title = slideLayout.findViewById(R.id.title);
        TextView description = slideLayout.findViewById(R.id.description);
        title.setText(mData.get(position).getTitle());
        description.setText(mData.get(position).getDescription());
        CircularProgressDrawable drawable = new CircularProgressDrawable(mContext);
        drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        drawable.start();
        Glide.with(mContext).load(mData.get(position).getImages()).placeholder(drawable).into(slideImage);
        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
