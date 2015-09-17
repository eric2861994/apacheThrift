namespace java if4031.common

typedef i32 userID
typedef string nickname
typedef string channelName
typedef i64 timestamp
typedef list<Message> messages

struct Message {
    1:nickname sender,
    2:channelName channel,
    3:string body,
    4:timestamp sendTime
}

service IRCService {

	/** Mendapatkan userID, untuk seterusnya userID akan digunakan oleh client untuk melakukan request ke server. */
	userID login(1:nickname nickname),
	/** Mengubah nickname dari user. */
    bool changeNickname(1:userID user, 2:nickname newNick),
    void logout(1:userID user),

    /** Masuk sebuah channel. */
    void joinChannel(1:userID user, 2:channelName channel),
    /** Meminta pesan */
    messages getMessage(1:userID user),
    /** Mengirim pesan ke channel tertentu, sekaligus mendapatkan pesan. */
    messages sendMessageToChannel(1:userID user, 2:channelName channel),
    /** Mengirim pesan ke channel semua channel yang didaftar oleh user, sekaligus mendapatkan pesan. */
    messages sendMessage(1:userID user),
    /** Keluar dari sebuah channel. */
    void leaveChannel(1:userID user, 2:channelName channel)
}
