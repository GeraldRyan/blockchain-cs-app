package privblock.gerald.ryan.mains;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Blockchain;

public class ApacheHttpMain {

	/*
	 * @Override public Object parse(InputStream jsonStream, String charset) throws
	 * InvalidJsonException { try { return new JSONTokener(new
	 * InputStreamReader(jsonStream, charset)).nextValue(); } catch
	 * (UnsupportedEncodingException e) { throw new JxsonPathException(e); } catch
	 * (JSONException e) { throw new InvalidJsonException(e); } }
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, ChainTooShortException,
			GenesisBlockInvalidException, BlocksInChainInvalidException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8080/CaseStudy/blockchain");
		CloseableHttpResponse response1;
		String response_string = "";
		try {
			response1 = httpclient.execute(httpGet);
//			System.out.println(response1.getStatusLine());
//			System.out.println(response1.toString());
			HttpEntity entity1 = response1.getEntity();
			Scanner sc = new Scanner(entity1.getContent());
			while (sc.hasNext()) {
				String next = sc.nextLine();
				response_string += next;
			}
//			System.out.println(response_string);
			String jsonString = response_string.replaceAll("</?[^>]+>", "").trim();
			System.out.println("JSon string");
			System.out.println(jsonString);
			Gson gson = new Gson();
			System.out.println("Deserialized");
			ArrayList<Block> chain = gson.fromJson(jsonString, new TypeToken<List<Block>>() {
			}.getType());
			Block block2 = gson.fromJson(
					"{\"id\":1,\"timestamp\":1,\"lastHash\":\"genesis_last_hash\",\"hash\":\"genesis_hash\",\"data\":[\"dance\",\"the\",\"tango\"],\"difficulty\":7,\"nonce\":1}",
					Block.class);
			System.out.println(chain);
			Blockchain bc = new Blockchain("New");
			bc.add_block("NEW INFO");
			System.out.println("WAS CHAIN REPLACED???");
			bc.replace_chain(chain);
			for (Block b : chain) {
				System.out.println(b.toString());
				System.out.println(b.getLastHash());
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}

}
