package com.example.booking_ma.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationsApprovalScreen;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.NotificationDisplayDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsAdapter  extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{

    private List<NotificationDisplayDTO> notifications;
    private Context context;
    private String token;

    public NotificationsAdapter(Context context, List<NotificationDisplayDTO> notifications, String token) {
        this.context = context;
        this.notifications = notifications;
        this.token = token;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        NotificationDisplayDTO item = notifications.get(position);

        holder.textViewNotificationFrom.setText("From : "+ item.getSenderId());
        holder.textViewNotificationMessage.setText("Message: "+ item.getMessage());
        holder.textViewNotificationType.setText("Type: "+ item.getNotificationType());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNotificationFrom, textViewNotificationMessage, textViewNotificationType;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNotificationFrom = itemView.findViewById(R.id.textViewNotificationFrom);
            textViewNotificationMessage = itemView.findViewById(R.id.textViewNotificationMessage);
            textViewNotificationType = itemView.findViewById(R.id.textViewNotificationType);

        }
    }
}
