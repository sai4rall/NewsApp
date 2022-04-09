package com.scube.localnews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.R;
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;
import com.scube.localnews.transformers.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class DashBoardFragement extends Fragment {
    IVerticalAdapter iVerticalAdapter;
    ItemCallback itemCallback;
    public DashBoardFragement(ItemCallback itemCallback, IVerticalAdapter iVerticalAdapter) {
        this.itemCallback=itemCallback;
        this.iVerticalAdapter=iVerticalAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dash_board_fragement, container, false);
        ViewPager2 viewPager2=v.findViewById(R.id.vertical_container_v2);
        ArrayList<NewsItem> newsItemList =itemCallback.getAllNewsItems();
        Toast.makeText(getActivity().getApplicationContext(),newsItemList.size()+"==",Toast.LENGTH_LONG).show();
        viewPager2.setClipToPadding(true);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        viewPager2.setAdapter(iVerticalAdapter.getVerticalViewPager2Adapter());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               itemCallback.putItem(iVerticalAdapter.getVerticalViewPager2Adapter().getSlideItems().get(position));
//                if(position==  verticalViewPager2Adapter.getSlideItems().size()-1){
//                    verticalViewPager2Adapter.setSlideItems(itemCallback.getAllNewsItems());
//                    verticalViewPager2Adapter.notifyDataSetChanged();
//                }
            }
        });
        return v;
    }

//    public List<NewsItem> generateData(int i) {
//        List<NewsItem> newsItemList =new ArrayList<>();
//        NewsItem s2 = new NewsItem("https://assets.eenadu.net/article_img/Harish-1a_5.jpg",
//                i++ + "Two Israeli firms used ForcedEntry exploit to spy on iPhone users: What it is, how to safeguard yourself\n", "హైదరాబాద్\u200C: వైద్య కళాశాలల ప్రతిపాదనల విషయంలో పార్లమెంటు, దేశాన్ని కేంద్ర ప్రభుత్వం తప్పుదోవ పట్టిస్తోందని రాష్ట్ర వైద్యారోగ్య శాఖ మంత్రి హరీశ్\u200C రావు ఆరోపించారు. కొత్త వైద్య కళాశాలల కోసం తెలంగాణ ప్రభుత్వం ప్రతిపాదనలు పంపలేదని పదేపదే అబద్ధాలు చెబుతోందని ఆక్షేపించారు. పార్లమెంటు సాక్షిగా గోబెల్స్ ప్రచారానికి దిగిందని మండిపడ్డారు. మొన్న గిరిజ\u200Cన రిజ\u200Cర్వేష\u200Cన్ల పెంపుపై ప్రతిపాద\u200Cన\u200Cలు తెలంగాణ నుంచి రాలేద\u200Cని చెప్పిన కేంద్రం.. ఇవాళ వైద్య కళాశాలల ఏర్పాటుపైనా లోకసభ వేదిక\u200Cగా దుష్ప్రచారం చేస్తోందని ఆగ్రహం వ్యక్తం చేశారు. తెలంగాణ నుంచి ఎలాంటి ప్రతిపాద\u200Cన\u200Cలు అంద\u200Cలేద\u200Cని కేంద్ర వైద్యారోగ్య శాఖ స\u200Cహాయ మంత్రి పార్లమెంటులో చెప్పడం బాధాకరమన్నారు. వైద్య కళాశాలలు మంజూరు చేయాలని పలుమార్లు కేంద్ర ప్రభుత్వాన్ని కోరినప్పటికీ ఒక్కటీ ఇవ్వకపోవడమే కాకుండా పార్లమెంటు సాక్షిగా ప\u200Cచ్చి అబ\u200Cద్ధాలు ఆడుతూ తెలంగాణ\u200Cపై అక్కసు వెళ్లగక్కుతూనే ఉందని మండిపడ్డారు. కేంద్రం స\u200Cహ\u200Cక\u200Cరించక\u200Cపోయినా ముఖ్యమంత్రి కేసీఆర్ నేతృత్వంలో జిల్లాకో వైద్య కళాశాల ఏర్పాటుకు తెలంగాణ ప్రభుత్వం అడుగులు వేస్తోందని హరీశ్\u200Cరావు తెలిపారు.",
//                "https://www.eenadu.net/telugu-news/sports/t20-league-8th-match-between-kolkata-and-panjab-live-match-updates/0400/122063914");
//        NewsItem s3 = new NewsItem("https://s.france24.com/media/display/9203d65e-adc6-11ec-aeb6-005056a90284/w:1280/p:16x9/f8657ae8a61ac1e55f5e96e502423163f6c9f844.jpg",
//                i++ + "NFT of Mandela's arrest warrant auctioned for $130,000", "South Africa's first democratic, black president was arrested on August 5, 1962, and later jailed for 27 years.\\n\\nThe reserve price at the Saturday night auction in Cape Town was 900,000 rand ($61,800), but the non-fungible tokens or NFT, \\\"sold for 1.9 million \n South Africa's first democratic, black president was arrested on August 5, 1962, and later jailed for 27 years.\\n\\nThe reserve price at the Saturday night auction in Cape Town was 900,000 rand ($61,800), but the non-fungible tokens or NFT, \\\"sold for 1.9 million",
//                "https://www.france24.com/en/live-news/20220327-nft-of-mandela-s-arrest-warrant-auctioned-for-130-000");
//        NewsItem s4 = new NewsItem("https://www.repstatic.it/content/nazionale/img/2022/03/25/172253159-22447455-b982-46ed-ba4b-a2da91bd546f.jpg", i++ + "Bitcoin: Unicredit perde il primo round in Bosnia contro un \\\"minatore\\\" che chiede 131 milioni di danni", "La causa è stata promossa a Banja Luka da una controllata della società italiana Bitminer Factory, che lamenta la chiusura dei conti correnti presso la filiale locale. La banca: \\\"Non instauriamo rapporti o collaborazioni con fornitori di valuta digitale La causa è stata promossa a Banja Luka da una controllata della società italiana Bitminer Factory, che lamenta la chiusura dei conti correnti presso la filiale locale. La banca: \\\"Non instauriamo rapporti o collaborazioni con fornitori di valuta digitale ",
//                "https://www.repubblica.it/economia/2022/03/27/news/bitcoin_unicredit_perde_il_primo_round_in_un_tribunale_bosniaco_contro_un_minatore_che_chiede_131_milioni_di_danni-342653477/");
////            slideItemsList.add(s1);
//        newsItemList.add(s2);
//        newsItemList.add(s3);
//        newsItemList.add(s4);
//        return newsItemList;
//    }
}