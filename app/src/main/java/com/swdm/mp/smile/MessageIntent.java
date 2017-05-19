package com.swdm.mp.smile;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ChaeRin on 2017-01-31.
 * the Message intent in Therapy fragment.
 * It shows a message by category randomly.
 */
public class MessageIntent extends Activity {
    static final String[] MESSAGE_LIST_MENU = {"Life", "Wisdom", "Confidence",
            "Money", "Failure", "Hope", "Challenge"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_intent);

        ListView listview;
        ListViewAdapter adapter;

        // Adapter ìƒì„±
        adapter = new ListViewAdapter();

        listview = (ListView) findViewById(R.id.message_listview);
        listview.setAdapter(adapter);
        // ì²« ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_life),
                MESSAGE_LIST_MENU[0]);
        // ë‘ ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_wisdom),
                MESSAGE_LIST_MENU[1]);
        // ì„¸ ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_confidence),
                MESSAGE_LIST_MENU[2]);
        // ë„¤ ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_money),
                MESSAGE_LIST_MENU[3]);
        // ë‹¤ì„¯ ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_failure),
                MESSAGE_LIST_MENU[4]);
        // ì—¬ì„¯ ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_hope),
                MESSAGE_LIST_MENU[5]);
        // ì¼ê³± ë²ˆì§¸ ì•„ì´í…œ ì¶”ê°€.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_challenge),
                MESSAGE_LIST_MENU[6]);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitle();
                Drawable iconDrawable = item.getIcon();

                createDialogBox(position);
            }
        });
    }
    public void onClick(View view){
        finish();
    }

    private AlertDialog createDialogBox(int position) {
        String title = "", msg = "", say = "";
        int img = 0;
        String[][] life = {
                {"They must often change who would be constant in happiness or wisdom. ",
                        "We did not change as we grew older; we just became more clearly ourselves. ",
                        "Certainly, travel is more than the seeing of sights; it is a change that goes on, deep and permanent, in the ideas of living. ",
                        "It's not true that life is one damn thing after another; it is one damn thing over and over.",
                        "We always overestimate the change that will occur in the next two years and underestimate the change that will occur in the next ten. Don't let yourself be lulled into inaction. ",
                        "Life is not fair; get used to it. ",
                        "What do you want a meaning for? Life is a desire, not a meaning. ",
                        "Life is a tragedy when seen in close-up, but a comedy in long-shot. "},
                {"Confucius", "Lynn Hall", "Miriam Beard", "Edna St. Vincent Millay", "Bill Gates",
                        "Bill Gates", "Charlie Chaplin", "Charlie Chaplin"}};
        String[][] wisdom = {{"If you can't make it good, at least make it look good.",
                "People always fear change. People feared electricity when it was invented, didn't they? People feared coal, they feared gas-powered engines... There will always be ignorance, and ignorance leads to fear. But with time, people will come to accept their silicon masters. ",
                "Where facts are few, experts are many. ",
                "It is not white hair that engenders wisdom. ",
                "Men are wise in proportion, not to their experience, but to their capacity for experience. ",
                "In a heated argument we are apt to lose sight of the truth. ",
                "Behind every argument is someone's ignorance.",
                "Fine words and an insinuating appearance are seldom associated with true virtue. ",
                "Man will occasionally stumble over the truth, but usually manages to pick himself up, walk over or around it, and carry on. ",
                "Don't pay any attention to what they write about you. Just measure it in inches. "},
                {"Bill Gates", "Bill Gates", "Donald R. Gannon", "Menander",
                        "James Boswell", "Publilius Syrus", "Louis D. Brandeis",
                        "Confucius", "Sir Winston Churchill", "Andy Warhol"}};
        String[][] confidence = {{"The word impossible is not in my dictionary",
                "If I'd had some set idea of a finish line, don't you think I would have crossed it years ago? ",
                "Use what talents you possess: the woods would be very silent if no birds sang there except those that sang best. ",
                "The more things a man is ashamed of, the more respectable he is. ",
                "The superior man cannot be known in little matters, but he may be entrusted with great concerns. The small man may not be entrusted with great concerns, but he may be known in little matters. ",
                "Everyone is born with genius, but most people only keep it a few minutes. ",
                "Martyrdom... is the only way in which a man can become famous without ability. ",
                "Ability is of little account without opportunity. ",
                "Great ability develops and reveals itself increasingly with every new assignment. ",
                "I'd rather be hated for who I am than be loved for who I'm not. ",
                "Hide not your talents. They for use were made. What's a sundial in the shade. ",
                "Better a diamond with a flaw than a pebble without. ",
                "He who despises himself, respects himself as one who despises. ",
                "Put yourself on view. This brings your talents to light. ",
                "The gratification comes in the doing, not in the results. "},
                {"Napoleon Bonaparte", "Bill Gates", "Henry Van Dyke",
                        "George Bernard Shaw", "Confucius", "Edgard Varese",
                        "George Bernard Shaw", "Napoleon Bonaparte", "Baltasar Gracian",
                        "Kurt Cobain", "Benjamin Franklin", "Confucius", "Friedrich Nietzsche"
                        , "Baltasar Gracian", "James Dean"}};
        String[][] money = {{"Like almost everyone who uses e-mail, I receive a ton of spam every day. Much of it offers to help me get out of debt or get rich quick. It would be funny if it weren't so exciting. ",
                "Who is rich? He that is content. Who is that? Nobody. ",
                "Nothing amuses me more than the easy manner with which everybody settles the abundance of those who have a great deal less than themselves. ",
                "A penny saved is a penny earned. ",
                "He that is of the opinion money will do everything may well be suspected of doing everything for money. ",
                "It is not easy for men to rise whose qualities are thwarted by poverty. ",
                "He is richest who is content with the least. ",
                "It is the sign of a weak mind to be unable to bear wealth. ",
                "Never spend your money before you have it. ",
                "He who will not economize will have to agonize. ",
                "A wise man should have money in his head, but not in his heart. "},
                {"Bill Gates", "Benjamin Franklin", "Jane Austen", "Benjamin Franklin",
                        "Benjamin Franklin", "Juvenal", "Socrates", "Seneca",
                        "Thomas Jefferson", "Confucius", "Jonathan Swift",}};
        String[][] failure = {{"It's fine to celebrate success but it is more important to heed the lessons of failure. ",
                "Blessed are the forgetful for they get the better even of their blunders. ",
                "You may be disappointed if you fail, but you are doomed if you don't try. ",
                "It's always helpful to learn from your mistakes because then your mistakes seem worthwhile. ",
                "Results! Why, man, I have gotten a lot of results. I know several thousand things that won't work. ",
                "Try as hard as we may for perfection, the net result of our labors is an amazing variety of imperfectness. We are surprised at our own versatility in being able to fail in so many different ways. ",
                "Mistakes are part of the dues one pays for a full life. ",
                "We're all capable of mistakes, but I do not care to enlighten you on the mistakes we may or may not have made. ",
                "If you have made mistakes, even serious ones, there is always another chance for you. What we call failure is not the falling down but the staying down. ",
                "Nothing has a stronger influence psychologically on their environment and especially on their children than the unlived life of the parent. ",
                "I daresay one profits more by the mistakes one makes off one's own bat than by doing the right thing on somebody's else advice. ",
                "We all have a few failures under our belt. It's what makes us ready for the successes. ",
                "Good people are good because they've come to wisdom through failure. ",
                "Be not ashamed of mistakes and thus make them crimes. ",
                "No one who cannot rejoice in the discovery of his own mistakes deserves to be called a scholar. ",
                "There's no such thing as failure. Mistakes happen in your life to bring into focus more clearly who you really are. ",
                "Mistakes, obviously, show us what needs improving. Without mistakes, how would we know what we had to work on? ",
                "Remember, you can always stoop and pick up nothing. ",
                "We think too much and feel too little. "},
                {"Bill Gates", "Friedrich Nietzsche", "Beverly Sills", "Garry Marshall",
                        "Tomas A. Edison", "Samuel McChord Crothers", "Sophia Loren", "Dan Quayle",
                        "Mary Pickford", "Carl Jung", "William Somerset Maugham", "Randy K. Milholland",
                        "William Saroyan", "Confucius", "Donald Foster", "Oprah Winfrey",
                        "Peter McWilliams", "Charlie Chaplin", "Charlie Chaplin"}};
        String[][] hope = {{"Yes, we can!",
                "In the end, everything is a gag. ",
                "Laughter is the tonic, the relief, the surcease for pain.",
                "To truly laugh, you must be able to take your pain, and play with it! ",
                "Those who dream by day are cognizant of many things which escape those who dream only by night. ",
                "Hope begins in the dark, the stubborn hope that if you just show up and try to do the right thing, the dawn will come. You wait and watch and work: You don't give up. ",
                "Hope is a waking dream. ",
                "Our problems are man-made, therefore they may be solved by man. And man can be as big as he wants. No problem of human destiny is beyond human beings. ",
                "To travel hopefully is a better thing than to arrive. ",
                "Perpetual optimism is a force multiplier. ",
                "The aim of life is self-development. To realize one's nature perfectly - that is what each of us is here for. ",
                "Fear cannot be without hope nor hope without fear. ",
                "Laughter is by definition healthy. ",
                "The more you find out about the world, the more opportunities there are to laugh at it. ",
                "An optimist is the human personification of spring. ",
                "I was irrevocably betrothed to laughter, the sound of which has always seemed to me to be the most civilized music in the world. "},
                {"Barack Obama", "Charlie Chaplin", "Charlie Chaplin", "Charlie Chaplin",
                        "Edgar Allan Poe", "Anne Lamott", "Aristotle", "John F. Kennedy",
                        "Robert Louis Stevenson", "Colin Powell", "Oscar Wilde", "Baruch Spinoza",
                        "Doris Lessing", "Bill Nye", "Susan J. Bissonette", "Havelock Ellis"}};
        String[][] challenge = {{"Actors search for rejection. If they don't get it they reject themselves. ",
                "Despair is a narcotic. It lulls the mind into indifference. ",
                "Never do things others can do and will do if there are things others cannot do or will not do. ",
                "Passion kept one fully in the present, so that time became a series of mutually exclusive 'nows.' ",
                "Lord, grant that I may always desire more than I accomplish.",
                "The biggest adventure you can ever take is to live the life of your dreams. ",
                "Never leave that 'till tomorrow which you can do today. ",
                "Only those who will risk going too far can possibly find out how far one can go. ",
                "If everything isn't black and white, I say, 'Why the hell not?' ",
                "Focusing your life solely on making a buck shows a certain poverty of ambition. It asks too little of yourself. Because it's only when you hitch your wagon to something larger than yourself that you realize your true potential. ",
                "Be wary of the man who urges an action in which he himself incurs no risk. ",
                "The secret of getting ahead is getting started. The secret of getting started is breaking your complex overwhelming tasks into small manageable tasks, and then starting on the first one. ",
                "There are risks and costs to a program of action. But they are far less than the long-range risks and costs of comfortable inaction. ",
                "Challenges are what make life interesting; overcoming them is what makes life meaningful. ",
                "Most of the important things in the world have been accomplished by people who have kept on trying when there seemed to be no hope at all. ",
                "Blaze with the fire that is never extinguished. ",
                "Judgement, not passion should prevail. ",
                "Great deeds are usually wrought at great risks. ",
                "Difficulties are meant to rouse, not discourage. The human spirit is to grow strong by conflict. ",
                "If you're never scared or embarrassed or hurt, it means you never take any chances. "},
                {"Charlie Chaplin", "Charlie Chaplin", "Amelia Earhart", "Sue Halpern",
                        "Michelangelo", "Oprah Winfrey", "Benjamin Franklin", "T. S. Eliot",
                        "John Wayne", "Barack Obama", "Joaquin Setanti", "Mark Twain",
                        "John F. Kennedy", "Joshua J. Marine", "Dale Carnegie", "Luisa Sigea",
                        "Epicharmus", "Herodotus", "William Ellery Channing", "Julia Sorel"}};
        if (position == 0) {//Life
            title = MESSAGE_LIST_MENU[0];
            int random = (int) (Math.random() * 8);
            msg = life[0][random] + "\n";
            msg += "\n- " + life[1][random];
            img = R.drawable.ic_life;
        } else if (position == 1) {//"Wisdom"
            title = MESSAGE_LIST_MENU[1];
            int random = (int) (Math.random() * 10);
            msg = wisdom[0][random] + "\n";
            msg += "\n- " + wisdom[1][random];
            img = R.drawable.ic_wisdom;
        } else if (position == 2) {//"Confidence"
            title = MESSAGE_LIST_MENU[2];
            int random = (int) (Math.random() * 15);
            msg = confidence[0][random] + "\n";
            msg += "\n- " + confidence[1][random];
            img = R.drawable.ic_confidence;
        } else if (position == 3) {//"Money"
            title = MESSAGE_LIST_MENU[3];
            int random = (int) (Math.random() * 11);
            msg = money[0][random] + "\n";
            msg += "\n- " + money[1][random];
            img = R.drawable.ic_money;
        } else if (position == 4) {//"Failure"
            title = MESSAGE_LIST_MENU[4];
            int random = (int) (Math.random() * 19);
            msg = failure[0][random] + "\n";
            msg += "\n- " + failure[1][random];
            img = R.drawable.ic_failure;
        } else if (position == 5) {//"Hope"
            title = MESSAGE_LIST_MENU[5];
            int random = (int) (Math.random() * 16);
            msg = hope[0][random] + "\n";
            msg += "\n- " + hope[1][random];
            img = R.drawable.ic_hope;
        } else if (position == 6) {//"Challenge"
            title = MESSAGE_LIST_MENU[6];
            int random = (int) (Math.random() * 20);
            msg = challenge[0][random] + "\n";
            msg += "\n- " + challenge[1][random];
            img = R.drawable.ic_challenge;
        }

        AlertDialog myDialogBox = new AlertDialog.Builder(this)
                .setTitle(title) //íŒì—…ì°½ íƒ€ì´í‹€ë°”
                .setMessage(msg)  //íŒì—…ì°½ ë‚´ìš©
                .setIcon(img)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        //ë‹«ê¸° ë²„íŠ¼ì„ ëˆ„ë¥´ë©´ ì•„ë¬´ê²ƒë„ ì•ˆí•˜ê³  ë‹«ê¸° ë•Œë¬¸ì— ê·¸ëƒ¥ ë¹„ì›€

                    }
                })
                .show(); // íŒì—…ì°½ ë³´ì—¬ì¤Œ
        return myDialogBox;
    }
}
